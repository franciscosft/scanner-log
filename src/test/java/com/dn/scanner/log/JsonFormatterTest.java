package com.dn.scanner.log;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.exception.FormatterException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonFormatterTest {

	@Test
	public void instanceOfTest() {
		Formatter formatter = new JsonFormatter();
		Assertions.assertTrue(formatter instanceof JsonFormatter);
		Assertions.assertFalse(formatter instanceof XMLFormatter);
	}
	
	@Test
	public void formatNotNullTest() throws FormatterException {
		Report report = TestUtils.getReport();
		Formatter formatter = new JsonFormatter();
		File format = formatter.format(report);
		Assertions.assertNotNull(format);
	}
	
	@Test
	public void formatJsonTest() throws FormatterException, StreamReadException, DatabindException, IOException {
		Report report = TestUtils.getReport();
		Formatter formatter = new JsonFormatter();
		File format = formatter.format(report);
		Assertions.assertNotNull(format);

		ObjectMapper objectMapper = new ObjectMapper();
		Report readValue = objectMapper.readValue(format, Report.class);
		Assertions.assertEquals(report, readValue);
	}


}
