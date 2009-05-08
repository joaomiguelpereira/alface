package org.nideasystems.scrumr.restlayer.restapi.authentication;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nideasystems.scrumr.restlayer.TestBase;
import org.restlet.Client;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

public class TestAuthetication extends TestBase {

	private static final String authenticationTokenPath = "user/authenticationToken";

	// @Ignore
	// public void testAuthorizationForWholeSite() {
	// Client client = new Client(Protocol.HTTP);
	// Response res = client.get(super.serviceUrl+authenticationTokenPath);
	// Assert.assertNotNull(res);
	// Assert.assertEquals(Status.CLIENT_ERROR_UNAUTHORIZED, res.getStatus());
	// }

	@Test
	public void testAuthorizationOk() {

		// Prepare the request
		Request request = new Request(Method.POST, super.serviceUrl
				+ authenticationTokenPath);

		// Add client authnetication
		// ChallengeScheme challengeSchema = ChallengeScheme.HTTP_COOKIE;

		// ChallengeResponse challengeResponse = new ChallengeResponse();
		Form loginForm = new Form();
		loginForm.add("username", "admin");
		loginForm.add("password", "admin");
		Representation repEnt = loginForm.getWebRepresentation();

		request.setEntity(repEnt);

		Client client = new Client(Protocol.HTTP);

		request.setEntity(repEnt);
		Response res = client.handle(request);
		Assert.assertNotNull(res);
		Assert.assertEquals(Status.SUCCESS_OK, res.getStatus());

		try {

			Object obj = JSONObject.stringToValue(res.getEntityAsText());

			JsonRepresentation rep = new JsonRepresentation(obj.toString());
			JSONObject jsonO = rep.getJsonObject();

			// TODO: Define error messages in JSON Format
			assertNotNull(jsonO.get("alfresco_ticket"));
			System.out.println("Authentication Tike:"+jsonO.get("alfresco_ticket"));

		} catch (Throwable e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			fail();
		}
	}

	@Test
	public void testAuthorizationFailBadCredentials() {

		// Prepare the request
		Request request = new Request(Method.POST, super.serviceUrl
				+ authenticationTokenPath);

		// Add client authnetication
		// ChallengeScheme challengeSchema = ChallengeScheme.HTTP_COOKIE;

		// ChallengeResponse challengeResponse = new ChallengeResponse();
		Form loginForm = new Form();
		loginForm.add("username", "admin");
		loginForm.add("password", "adminas");
		Representation repEnt = loginForm.getWebRepresentation();

		request.setEntity(repEnt);

		Client client = new Client(Protocol.HTTP);

		request.setEntity(repEnt);
		Response res = client.handle(request);
		Assert.assertNotNull(res);

		try {

			Object obj = JSONObject.stringToValue(res.getEntityAsText());

			JsonRepresentation rep = new JsonRepresentation(obj.toString());
			JSONObject jsonO = rep.getJsonObject();

			// TODO: Define error messages in JSON Format
			assertNotNull(jsonO.get("error"));

		} catch (Throwable e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			fail();
		}

		Assert.assertEquals(Status.CLIENT_ERROR_FORBIDDEN, res.getStatus());
	}

	@Test
	public void testAuthorizationFailNoFormProvided() {

		// Prepare the request
		Request request = new Request(Method.POST, super.serviceUrl
				+ authenticationTokenPath);

		// Add client authnetication
		// ChallengeScheme challengeSchema = ChallengeScheme.HTTP_COOKIE;

		// ChallengeResponse challengeResponse = new ChallengeResponse();

		Client client = new Client(Protocol.HTTP);

		Response res = client.handle(request);
		Assert.assertNotNull(res);
		Assert.assertEquals(Status.CLIENT_ERROR_FORBIDDEN, res.getStatus());
	}

	@Test
	public void testAuthorizationFailNoAlfrescoUp() {

		// TODO: Find a way to change config of the current thread

		// test.alfresco.broken

		// TODO: Add system environment

		// Prepare the request
		Request request = new Request(Method.POST, super.serviceUrl
				+ authenticationTokenPath);

		// Add client authnetication
		// ChallengeScheme challengeSchema = ChallengeScheme.HTTP_COOKIE;

		// ChallengeResponse challengeResponse = new ChallengeResponse();

		Client client = new Client(Protocol.HTTP);

		Response res = client.handle(request);
		Assert.assertNotNull(res);
		Assert.assertEquals(Status.SERVER_ERROR_INTERNAL, res.getStatus());
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
