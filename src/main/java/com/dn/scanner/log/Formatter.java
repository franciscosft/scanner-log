package com.dn.scanner.log;

import com.dn.scanner.log.dto.Report;

public interface Formatter {

	public default void format(Report report){}
	
}
