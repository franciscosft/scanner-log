package com.dn.scanner.log;

import java.io.File;
import java.io.IOException;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.exception.FormatterException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class XMLFormatter implements Formatter {

	public File format(Report report) throws FormatterException {
		try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
 
            File createTempFile = File.createTempFile("report", ".xml");
            jaxbMarshaller.marshal(report, createTempFile);
             
			System.out.println("The XML document is: " + createTempFile.getAbsolutePath());
			return createTempFile;
        } catch (JAXBException | IOException e) {
        	e.printStackTrace();
			System.err.println("Something wrong happen to save the file");
			throw new FormatterException();
        }
	}

}
