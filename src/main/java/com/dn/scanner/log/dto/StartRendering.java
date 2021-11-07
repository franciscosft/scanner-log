package com.dn.scanner.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartRendering {
	String timestamp;
	String thread;
	String uid;
}