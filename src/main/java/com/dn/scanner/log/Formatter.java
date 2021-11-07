package com.dn.scanner.log;

import java.io.File;

import com.dn.scanner.log.dto.Report;
import com.dn.scanner.log.exception.FormatterException;

public interface Formatter {

	File format(Report report) throws FormatterException;	
}
