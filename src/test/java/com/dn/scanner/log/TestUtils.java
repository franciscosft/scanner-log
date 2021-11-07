package com.dn.scanner.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dn.scanner.log.dto.Rendering;
import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.dto.Summary;

public class TestUtils {
	
	public static Report getReport() {
		Report report = new Report();
		List<Rendering> rendering = new ArrayList<>();
		rendering.add(new Rendering("1234", "0", "1234123-12", Arrays.asList("2010-10-06 11:01:54,313"),
				Arrays.asList("2010-10-06 10:23:02,634")));
		report.setRendering(rendering);
		report.setSummary(new Summary(1, 0, 0));
		return report;
	}

}
