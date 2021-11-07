package com.dn.scanner.log.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dn.scanner.log.App;
import com.dn.scanner.log.util.ScannerUtils;

public class ScannerUtilsTest {
	
	@Test
	public void pocTest() {
		File logFile = getLogFile();	
		Assertions.assertNotNull(logFile);
	}

	private File getLogFile() {
		try (InputStream resourceAsStream = App.class.getResourceAsStream("/server.log")) {
    		File createTempFile = File.createTempFile("temp",".log");
    		Files.copy(resourceAsStream, createTempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    		return createTempFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	public void loadLogFileTest() throws URISyntaxException {
//		File loadLogFile = ScannerUtils.loadLogFile();
//		Assertions.assertNotNull(loadLogFile);
		
//		URL resource = ScannerUtils.class.getResource("/server.log");
//		Path path = Path.of(resource.toURI());
//		
//		Assertions.assertEquals(loadLogFile.length(), path.toFile().length());
	}

}
