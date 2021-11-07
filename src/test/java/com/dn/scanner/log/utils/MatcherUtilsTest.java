package com.dn.scanner.log.utils;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dn.scanner.log.dto.GetRendering;
import com.dn.scanner.log.util.MatcherUtils;
import com.dn.scanner.log.dto.DocumentRequest;

public class MatcherUtilsTest {

	@Test
	public void getDocumentRequestTest() {
		String log = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<DocumentRequest> documentRequest = MatcherUtils.getDocumentRequest(log);
		Assertions.assertFalse(documentRequest.isEmpty());
		Assertions.assertEquals("2010-10-06 09:02:13,631", documentRequest.get().getTimestamp());
		Assertions.assertEquals("WorkerThread-2", documentRequest.get().getThread());
		Assertions.assertEquals("0", documentRequest.get().getPage());
		Assertions.assertEquals("114466", documentRequest.get().getDocumentId());
	}
	
	@Test
	public void getUidTest() {
		String log = "2010-10-06 09:02:14,825 [WorkerThread-0] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<String> uidGet = MatcherUtils.getUidGet(log);
		Assertions.assertEquals("1286373733634-5423", uidGet.get());
	}

	@Test
	public void getRenderingTest() {
		String log = "2010-10-06 09:02:14,825 [WorkerThread-0] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<GetRendering> getRendering = MatcherUtils.getGetRendering(log);
		Assertions.assertEquals("2010-10-06 09:02:14,825", getRendering.get().getTimestamp());
		Assertions.assertEquals("1286373733634-5423", getRendering.get().getUid());
	}

}
