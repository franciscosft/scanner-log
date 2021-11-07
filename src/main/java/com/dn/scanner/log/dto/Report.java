package com.dn.scanner.log.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
	List<Rendering> rendering;
	Summary summary;
}