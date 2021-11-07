package com.dn.scanner.log.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScannerUtils {
	
	public static File loadLogFile() throws IOException {
		try (InputStream resourceAsStream = ScannerUtils.class.getResourceAsStream("/server.log")) {
    		File createTempFile = File.createTempFile("temp",".log");
    		Files.copy(resourceAsStream, createTempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    		return createTempFile;
		} catch (IOException e) {
			throw e;
		}
	}

}
