package org.nideasystems.web20.poc.weblayer.authentication;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nideasystems.web20.poc.weblayer.TestBase;
import org.restlet.Client;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

@Ignore
public class TestAuthetication extends TestBase {

	@Ignore
	public void testAuthorizationForWholeSite() {
		Client client = new Client(Protocol.HTTP);
		Response res = client.get(super.serviceUrl);
		Assert.assertNotNull(res);
		Assert.assertEquals(Status.CLIENT_ERROR_UNAUTHORIZED, res.getStatus());				
	}

	@Ignore
	public void testAuthorization() {
		
		
		//Prepare the request
		Request request = new Request(Method.GET,super.serviceUrl);
		
		//Add client authnetication
		ChallengeScheme challengeSchema = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse challengeResponse = new ChallengeResponse(challengeSchema, "scot","tiger");
		request.setChallengeResponse(challengeResponse);
		
		Client client = new Client(Protocol.HTTP);
		Response res = client.handle(request);
		Assert.assertNotNull(res);
		Assert.assertEquals(Status.SUCCESS_OK, res.getStatus());				
	}

	@Before
	public void tearUp() {
		super.setUp();
	}
	@After
	public void DownUp() {
		super.tearDown();
	}
	
}
