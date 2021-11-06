package com.dn.scanner.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.dto.Summary;

public class JsonFormatter implements Formatter {

	@Override
	public void format(Report report) {
		JSONObject content = new JSONObject();
		JSONArray renderings = new JSONArray();
		report.getRenderings().forEach(rendering -> {
			JSONObject renderingJson = new JSONObject();
			renderingJson.put("document", rendering.getDocumentId());
			renderingJson.put("page", rendering.getPage());
			renderingJson.put("uid", rendering.getUid());

			JSONArray starts = new JSONArray();
			starts.putAll(rendering.getStart());
			renderingJson.put("start", starts);

			JSONArray gets = new JSONArray();
			gets.putAll(rendering.getGet());
			renderingJson.put("get", gets);

			renderings.put(renderingJson);
		});
		content.put("rendering", renderings);

		Summary summary = report.getSummary();
		JSONObject summaryJson = new JSONObject();
		summaryJson.put("count", summary.getCount());
		summaryJson.put("duplicates", summary.getDuplicates());
		summaryJson.put("unnecessary", summary.getUnnecessary());

		content.put("summary", summaryJson);

		JSONObject reportJson = new JSONObject();
		reportJson.put("report", content);

		try {
			File createTempFile = File.createTempFile("report", ".json");
			FileWriter fileWriter = new FileWriter(createTempFile);
			fileWriter.write(reportJson.toString(4));

			System.err.println("The document is: " + createTempFile.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Something wrong happen to save the file");
		}
	}

}
