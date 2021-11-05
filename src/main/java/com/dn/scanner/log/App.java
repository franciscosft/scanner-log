package com.dn.scanner.log;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class App {
    
    public static void main(String[] args) {
    	URL resource = App.class.getResource("/server.log");
    	try {
			RenderingLogScanner.getRenderingStatistics(Path.of(resource.toURI()));
		} catch (IOException | URISyntaxException e) {
			System.err.println("It was not possible find server.log");
		}
    }
    
}
