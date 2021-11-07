package com.dn.scanner.log;

import java.net.URL;

import org.junit.jupiter.api.Test;

public class AppTest {

	/**
	 * The execution would be with errors	
	 */
	@Test
	public void appErrorTest() {
		String[] args = { "path" };
		App.main(args);
	}
	
	/**
	 * The execution would be without errors	
	 */
	@Test
	public void appArgumentCorrectTest() {
		URL resource = AppTest.class.getResource("/server.log");
		String[] args = { resource.getPath() };
		App.main(args);
	}
	
	/**
	 * The execution would be without errors	
	 */
	@Test
	public void withOutArgumentsTest() {
		App.main(null);
	}

}
