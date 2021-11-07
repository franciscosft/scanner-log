package com.dn.scanner.log.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dn.scanner.log.util.ScannerUtils;

public class ScannerUtilsTest {
	
	@Test
	public void loadLogFileTest() throws URISyntaxException {
		File loadLogFile = ScannerUtils.loadLogFile();
		Assertions.assertNotNull(loadLogFile);
		
//		URL resource = ScannerUtils.class.getResource("/server.log");
//		Path path = Path.of(resource.toURI());
//		
//		Assertions.assertEquals(loadLogFile.length(), path.toFile().length());
	}

}
