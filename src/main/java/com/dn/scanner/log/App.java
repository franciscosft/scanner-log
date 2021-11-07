package com.dn.scanner.log;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import com.dn.scanner.log.util.ScannerUtils;

public class App {
    
    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
    	
    	File loadLogFile = ScannerUtils.loadLogFile();
    	if (loadLogFile != null) {
    		try {
    			RenderingLogScanner.getRenderingStatistics(loadLogFile.toPath());
    		} catch (IOException e) {
    			System.err.println("It was not possible find server.log");
    		}
    	} else {
    		System.err.println("It was not possible get server.log");
    	}
    	
    	long end = System.currentTimeMillis();
    	long executionTime = (end - start);
		System.out.println("Execution time: " + executionTime + " milli seconds") ;
    }

}
