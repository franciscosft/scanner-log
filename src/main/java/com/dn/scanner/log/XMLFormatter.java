package com.dn.scanner.log;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.dto.Summary;

public class XMLFormatter implements Formatter {

	@Override
	public void format(Report report) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xml = docBuilder.newDocument();

			Element reportElement = xml.createElement("report");
			xml.appendChild(reportElement);

			report.getRenderings().forEach(rendering -> {
				Element renderingElement = xml.createElement("rendering");
				reportElement.appendChild(renderingElement);

				Element document = xml.createElement("document");
				document.setTextContent(rendering.getDocumentId());
				renderingElement.appendChild(document);
				
				Element page = xml.createElement("page");
				page.setTextContent(rendering.getPage());
				renderingElement.appendChild(page);

				Element uid = xml.createElement("uid");
				uid.setTextContent(rendering.getUid());
				renderingElement.appendChild(uid);

				rendering.getStart().forEach(s -> {
					Element start = xml.createElement("start");
					start.setTextContent(s);
					renderingElement.appendChild(start);
				});

				rendering.getGet().forEach(g -> {
					Element get = xml.createElement("get");
					get.setTextContent(g);
					renderingElement.appendChild(get);
				});
			});

			Element summaryElement = xml.createElement("summary");
			reportElement.appendChild(summaryElement);

			Summary summary = report.getSummary();

			Element count = xml.createElement("count");
			count.setTextContent(summary.getCount() + "");
			summaryElement.appendChild(count);

			Element duplicates = xml.createElement("duplicates");
			duplicates.setTextContent(summary.getDuplicates() + "");
			summaryElement.appendChild(duplicates);

			Element unnecessary = xml.createElement("unnecessary");
			unnecessary.setTextContent(summary.getUnnecessary() + "");
			summaryElement.appendChild(unnecessary);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			DOMSource source = new DOMSource(xml);

			File createTempFile = File.createTempFile("report", ".xml");
			StreamResult result = new StreamResult(createTempFile);
			transformer.transform(source, result);

			System.err.println("The document is: " + createTempFile.getAbsolutePath());
		} catch (ParserConfigurationException | TransformerException e) {
			System.err.println("Something wrong happen during XML parser");
		} catch (IOException e) {
			System.err.println("Something wrong happen to save the file");
		}

	}

}
