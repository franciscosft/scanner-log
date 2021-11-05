package com.dn.scanner.log.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rendering {
	String documentId;
	String page;
	String uid;
	List<String> start = new ArrayList<>();
	List<String> get = new ArrayList<>();
}