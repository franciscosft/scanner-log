package com.dn.scanner.log;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.exception.FormatterException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class XMLFormatterTest {

	@Test
	public void instanceOfTest() {
		Formatter xmlFormatter = new XMLFormatter();
		
		Assertions.assertTrue(xmlFormatter instanceof XMLFormatter);
		Assertions.assertFalse(xmlFormatter instanceof JsonFormatter);
	}
	
	@Test
	public void formatNotNullTest() throws FormatterException, JAXBException {
		Report report = TestUtils.getReport();
		Formatter xmlFormatter = new XMLFormatter();
		File format = xmlFormatter.format(report);
		Assertions.assertNotNull(format);

	}

	@Test
	public void unmarshllerTest() throws JAXBException, FormatterException {
		Report report = TestUtils.getReport();
		Formatter xmlFormatter = new XMLFormatter();
		File format = xmlFormatter.format(report);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Report unmarshal = (Report) unmarshaller.unmarshal(format);

		Assertions.assertEquals(report, unmarshal);
	}


}
