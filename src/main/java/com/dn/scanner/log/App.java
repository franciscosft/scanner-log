package com.dn.scanner.log;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.dn.scanner.log.util.ScannerUtils;

public class App {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			File loadLogFile = ScannerUtils.getLogFile(args);
			RenderingLogScanner.getRenderingStatistics(loadLogFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("It was not possible find server.log");
		}

		long end = System.currentTimeMillis();
		long executionTime = (end - start);
		System.out.println("Execution time: " + executionTime + " milli seconds");
	}

}
