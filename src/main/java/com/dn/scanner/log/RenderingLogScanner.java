package com.dn.scanner.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.dn.scanner.log.dto.DocumentRequest;
import com.dn.scanner.log.dto.GetRendering;
import com.dn.scanner.log.dto.Rendering;
import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.dto.Summary;

public class RenderingLogScanner {

	private static final String RENDERING_START = "Executing request startRendering";
	private static final String RENDERING_ID = "Service startRendering returned";
	private static final String RENDERING_GET = "Executing request getRendering with arguments ";

	public static void getRenderingStatistics(Path logFile) throws IOException {
		Map<String, DocumentRequest> threadContext = new HashMap<>();
		Map<DocumentRequest, Rendering> startRenderingMap = new HashMap<>();
		Map<DocumentRequest, Rendering> getRenderingMap = new HashMap<>();

		Files.lines(logFile).forEach(line -> {
			// System.err.println(line);
			if (line.contains(RENDERING_START)) {
				// System.err.println(line);
				DocumentRequest documenteRequest = MatcherUtils.getDocumentRequest(line).orElseThrow();
				String threadName = documenteRequest.getThread();
//				System.err.println(threadName + " ---- " + documenteRequest);
				threadContext.put(threadName, documenteRequest);
			} else if (line.contains(RENDERING_ID)) {
				// System.err.println(line);
				String uid = MatcherUtils.getUidStart(line).orElseThrow();
				String threadName = MatcherUtils.getThread(line).orElseThrow();
				DocumentRequest documentRequest = threadContext.get(threadName);
				if (documentRequest != null) {
					Rendering rendering = startRenderingMap.computeIfAbsent(documentRequest,
							docReq -> new Rendering(documentRequest.getDocumentId(), documentRequest.getPage(), uid,
									new ArrayList<>(), new ArrayList<>()));
					rendering.getStart().add(documentRequest.getTimestamp());
				}
			} else if (line.contains(RENDERING_GET)) {
				GetRendering getRendering = MatcherUtils.getGetRendering(line).orElseThrow();
				String uidGetRendering = getRendering.getUid();

				startRenderingMap.entrySet().forEach(e -> {
					if (e.getValue().getUid().equals(uidGetRendering)) {
						e.getValue().getGet().add(getRendering.getTimestamp());
					}
				});
			}
		});

		System.err.println("----- Report ------");
		System.err.println(startRenderingMap.size());
		startRenderingMap.values().forEach(s -> {
			System.err.println(s.getDocumentId() + " | " + s.getPage() + " | " + s.getUid() + " | "
					+ s.getStart().size() + " | " + s.getGet().size());
		});

		Report report = new Report();
		report.setRenderings(startRenderingMap.values().stream().collect(Collectors.toList()));

		Summary summary = new Summary();
		summary.setCount(startRenderingMap.size());
		summary.setDuplicates(getDuplicates(startRenderingMap));
		summary.setUnnecessary(getUnnecessary(startRenderingMap));
		report.setSummary(summary);

		System.out.println(summary);
	}

	private static int getUnnecessary(Map<DocumentRequest, Rendering> startRenderingMap) {
		return (int) startRenderingMap.values().stream().filter(s -> s.getGet().isEmpty()).count();
	}

	private static int getDuplicates(Map<DocumentRequest, Rendering> startRenderingMap) {
		return (int) startRenderingMap.values().stream().filter(s -> s.getStart().size() > 1).count();
	}

}
