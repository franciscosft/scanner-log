package com.dn.scanner.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 2010-10-06 09:03:57,879 [WorkerThread-14] INFO [ServiceProvider]: Executing
// request startRendering with arguments [114273, 0] on service object
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
	String timestamp;
	String thread;
	String documentId;
	String page;
}