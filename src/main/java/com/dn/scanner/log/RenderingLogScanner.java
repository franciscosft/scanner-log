package com.dn.scanner.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dn.scanner.log.dto.DocumentRequest;
import com.dn.scanner.log.dto.GetRendering;
import com.dn.scanner.log.dto.Rendering;
import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.dto.StartRendering;
import com.dn.scanner.log.dto.Summary;
import com.dn.scanner.log.exception.FormatterException;
import com.dn.scanner.log.util.MatcherUtils;

public class RenderingLogScanner {

	private static final String RENDERING_START = "Executing request startRendering";
	private static final String RENDERING_ID = "Service startRendering returned";
	private static final String RENDERING_GET = "Executing request getRendering with arguments ";

	public static void getRenderingStatistics(Path logFile) throws IOException {
		Map<DocumentRequest, Rendering> renderingMap = generateReport(logFile);
		Report report = getReport(renderingMap);
		formatReport(report);
	}

	private static Map<DocumentRequest, Rendering> generateReport(Path logFile) throws IOException {
		Map<String, DocumentRequest> threadContext = new HashMap<>();
		Map<DocumentRequest, Rendering> renderingMap = new HashMap<>();

		Files.lines(logFile).forEach(line -> {
			if (line.contains(RENDERING_START)) {
				DocumentRequest documenteRequest = MatcherUtils.getDocumentRequest(line).orElseThrow();
				String threadName = documenteRequest.getThread();
				threadContext.put(threadName, documenteRequest);
			} else if (line.contains(RENDERING_ID)) {
				StartRendering startRendering = MatcherUtils.getStartRendering(line).orElseThrow();
				String uid = startRendering.getUid();
				String threadName = startRendering.getThread();
				String timestamp = startRendering.getTimestamp();
				
				DocumentRequest documentRequest = threadContext.get(threadName);
				if (documentRequest != null) {
					Rendering rendering = renderingMap.computeIfAbsent(documentRequest,
							docReq -> new Rendering(documentRequest.getDocumentId(), documentRequest.getPage(), uid,
									new ArrayList<>(), new ArrayList<>()));
					rendering.getStart().add(timestamp);
				}
			} else if (line.contains(RENDERING_GET)) {
				GetRendering getRendering = MatcherUtils.getGetRendering(line).orElseThrow();
				String uidGetRendering = getRendering.getUid();

				renderingMap.entrySet().forEach(e -> {
					if (e.getValue().getUid().equals(uidGetRendering)) {
						e.getValue().getGet().add(getRendering.getTimestamp());
						return;
					}
				});
			}
		});
		return renderingMap;
	}

	private static void formatReport(Report report) {
		List<Formatter> formatters = new ArrayList<>(Arrays.asList(new XMLFormatter(), new JsonFormatter()));
		formatters.forEach(formatter -> {
			try {
				formatter.format(report);
			} catch (FormatterException e) {
				e.printStackTrace();
				System.err.println("Occurred some problem to format the report");
			}
		});
	}

	private static Report getReport(Map<DocumentRequest, Rendering> renderingMap) {
		Report report = new Report();
		report.setRendering(renderingMap.values().stream().collect(Collectors.toList()));

		Summary summary = new Summary();
		summary.setCount(renderingMap.size());
		summary.setDuplicates(getDuplicates(renderingMap));
		summary.setUnnecessary(getUnnecessary(renderingMap));
		report.setSummary(summary);
		return report;
	}

	private static int getUnnecessary(Map<DocumentRequest, Rendering> startRenderingMap) {
		return (int) startRenderingMap.values().stream().filter(s -> s.getGet().isEmpty()).count();
	}

	private static int getDuplicates(Map<DocumentRequest, Rendering> startRenderingMap) {
		return (int) startRenderingMap.values().stream().filter(s -> s.getStart().size() > 1).count();
	}

}
