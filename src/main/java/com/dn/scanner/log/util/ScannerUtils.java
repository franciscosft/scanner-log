package com.dn.scanner.log.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.StringUtils;

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
	
	public static File getLogFile(String[] args) throws IOException {
		File loadLogFile;
		if(!StringUtils.isAllBlank(args)){
			String path = args[0];
			loadLogFile = new File(path);
			if(loadLogFile.length() == 0) {
				System.out.println("The path " + args + " is not correct. Please, verify and try again!");
				throw new IOException("This file doesn't exist!");
			}
		} else {
			loadLogFile = ScannerUtils.loadLogFile();
		}
		return loadLogFile;
	}

}
