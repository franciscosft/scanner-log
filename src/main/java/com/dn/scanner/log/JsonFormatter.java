package com.dn.scanner.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.exception.FormatterException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonFormatter implements Formatter {

	@Override
	public File format(Report report) throws FormatterException {
		try {
			File createTempFile = File.createTempFile("report", ".json");
			FileWriter fileWriter = new FileWriter(createTempFile);
			try (fileWriter) {
				ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
				String json = mapper.writeValueAsString(report);
				fileWriter.write(json);
			}
			System.out.println("The JSON document is: " + createTempFile.getAbsolutePath());
			return createTempFile;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Something wrong happen to save the file");
			throw new FormatterException();
		} 
	}

}
