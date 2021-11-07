package com.dn.scanner.log.utils;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dn.scanner.log.dto.DocumentRequest;
import com.dn.scanner.log.dto.GetRendering;
import com.dn.scanner.log.dto.StartRendering;
import com.dn.scanner.log.util.MatcherUtils;

public class MatcherUtilsTest {

	@Test
	public void getUidStartTest() {
		String log = "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423";
		Optional<StartRendering> startRendering = MatcherUtils.getStartRendering(log);
		Assertions.assertFalse(startRendering.isEmpty());
		Assertions.assertEquals("1286373733634-5423", startRendering.get().getUid());
		Assertions.assertEquals("2010-10-06 09:02:13,634", startRendering.get().getTimestamp());
		Assertions.assertEquals("WorkerThread-2", startRendering.get().getThread());
	}

	@Test
	public void getUidStartNotMatchTest() {
		String log = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<StartRendering> startRendering = MatcherUtils.getStartRendering(log);
		Assertions.assertTrue(startRendering.isEmpty());
	}

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
	public void getDocumentRequestNotMatchTest() {
		String log = "2010-10-06 09:02:14,825 [WorkerThread-0] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<DocumentRequest> documentRequest = MatcherUtils.getDocumentRequest(log);
		Assertions.assertTrue(documentRequest.isEmpty());
	}

	@Test
	public void getUidTest() {
		String log = "2010-10-06 09:02:14,825 [WorkerThread-0] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<String> uidGet = MatcherUtils.getUidGet(log);
		Assertions.assertFalse(uidGet.isEmpty());
		Assertions.assertEquals("1286373733634-5423", uidGet.get());
	}

	@Test
	public void getUidNotMatchTest() {
		String log = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<String> uidGet = MatcherUtils.getUidGet(log);
		Assertions.assertTrue(uidGet.isEmpty());
	}

	@Test
	public void getRenderingTest() {
		String log = "2010-10-06 09:02:14,825 [WorkerThread-0] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<GetRendering> getRendering = MatcherUtils.getGetRendering(log);
		Assertions.assertFalse(getRendering.isEmpty());
		Assertions.assertEquals("2010-10-06 09:02:14,825", getRendering.get().getTimestamp());
		Assertions.assertEquals("1286373733634-5423", getRendering.get().getUid());
	}

	@Test
	public void getRenderingNotMachTest() {
		String log = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
		Optional<GetRendering> getRendering = MatcherUtils.getGetRendering(log);
		Assertions.assertTrue(getRendering.isEmpty());
	}

}
