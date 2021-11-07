package com.dn.scanner.log.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlType(propOrder={"documentId", "page" , "uid", "start", "get" })
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