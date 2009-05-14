package org.nideasystems.scrumr.restlayer.restapi.authentication;

import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nideasystems.scrumr.common.SharedConstants;
import org.nideasystems.scrumr.restlayer.TestBase;
import org.nideasystems.scrumr.restlayer.util.BasicCookieUtils;
import org.nideasystems.scrumr.security.services.BasicSecurityService;
import org.nideasystems.scrumr.security.services.ISecurityService;
import org.restlet.Client;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.engine.util.Base64;
import org.restlet.engine.util.CookieSettingSeries;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

public class TestAuthetication extends TestBase {

	private static final String authenticationTokenPath = "user/authenticationToken";

	private CookieSetting createDummyCookie() {
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.append(
					SharedConstants.Json.AUTHENTICATION_TOKEN_ALFRESCO_TICKET,
					"tiketc");
			jsonObject.append(SharedConstants.Json.AUTHENTICATION_TOKEN_MAX_AGE,
					10);

			jsonObject.append(SharedConstants.Json.AUTHENTICATION_TOKEN_USERNAME,
					"admin");

			jsonObject.append(
					SharedConstants.Json.AUTHENTICATION_TOKEN_ACCEPT_COOKIE,
					"true");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String jsonAsString = jsonObject.toString();
		
		String name = securityService
				.getHashFromValue("AuthenticationCookieSetting");

		CookieSetting cookieSetting = BasicCookieUtils
				.createAuthenticationCookieSetting(name, 10);
		cookieSetting.setValue(securityService
				.getValueTo64BasedEncoded(jsonAsString));
		
		return cookieSetting;


	}

	public void testLogoutOk() {
		// As a User I want to logout, destroying all my session data
		Request request = new Request(Method.DELETE, super.serviceUrl
				+ authenticationTokenPath+"/"+"alfreco ticket");

		
		// Create a client connector
		Client client = new Client(Protocol.HTTP);

		//I have to send the server the Alfresco ticket at least
		//Form form = new Form();
		//form.add(SharedConstants.Json.AUTHENTICATION_TOKEN_ALFRESCO_TICKET,"tiketc");
		//form.add("key", securityService.getHashFromValue(ISecurityService.SECRET));
		//request.setEntity(form.getWebRepresentation());
		
		// Create a dummy authentication token cookie
		CookieSetting cookie = null;
		
		request.getCookies().add(createDummyCookie());
		// Let the connector send the request and get a response
		Response res = client.handle(request);
		
		assertEquals(Status.SUCCESS_OK, res.getStatus());

	}

	// @Ignore
	// public void testAuthorizationForWholeSite() {
	// Client client = new Client(Protocol.HTTP);
	// Response res = client.get(super.serviceUrl+authenticationTokenPath);
	// Assert.assertNotNull(res);
	// Assert.assertEquals(Status.CLIENT_ERROR_UNAUTHORIZED, res.getStatus());
	// }

	@Test
	public void testAuthorizationOk() {

		// * As a User, I want to send a form with my username/password, so I
		// can authenticate my self and use the services
		// 
		// * As a User Agent I want to receive a JSON String representing the
		// AuthenticationToken if the username/password is ok (the
		// authenticationToken is base64Encoded with alfrescoAuthTicket:String,
		// username:String, maxAge:Integer)
		//
		// * As a User Agent I Want to receive a Cookie with the Authentication
		// Token if the username/passwork is ok (cookie has a JSON
		// Representation of the ticket

		// Prepare the request
		Request request = new Request(Method.POST, super.serviceUrl
				+ authenticationTokenPath+"/teste");

		// Create the form
		Form loginForm = new Form();
		loginForm.add("username", "admin");
		loginForm.add("password", "admin");
		
		
		
		// I want to get also the authentication cookie

		loginForm.add("cookie", "true");
		// I also want to set the time of validity of the token/cookie (now just
		// cookie is working)
		loginForm.add("maxAge", "10");

		Representation repEnt = loginForm.getWebRepresentation();
		// Set the form as an Entity
		request.setEntity(repEnt);

		// Create a client connector
		Client client = new Client(Protocol.HTTP);

		// Add the entity to the request
		request.setEntity(repEnt);
		// Let the connector send the request and get a response
		Response res = client.handle(request);
		// the response should not be null
		Assert.assertNotNull(res);
		// the status of the Server is OK
		Assert.assertEquals(Status.SUCCESS_OK, res.getStatus());

		// we have a JSonRespresentation of the the AUthenticationToken
		String authTokenAsString = res.getEntityAsText();
		try {
			JSONObject jsonObj = new JSONObject(authTokenAsString);
			System.out.println(authTokenAsString);
			assertNotNull(jsonObj
					.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_ALFRESCO_TICKET));
			assertNotNull(jsonObj
					.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_USERNAME));
			assertNotNull(jsonObj
					.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_MAX_AGE));
			assertNotNull(jsonObj
					.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_ACCEPT_COOKIE));

			boolean acceptCoockie = ((JSONArray) jsonObj
					.get(SharedConstants.Json.AUTHENTICATION_TOKEN_ACCEPT_COOKIE))
					.getBoolean(0);

			assertEquals(true, acceptCoockie);

			int maxAge = ((JSONArray) jsonObj
					.get(SharedConstants.Json.AUTHENTICATION_TOKEN_MAX_AGE))
					.getInt(0);

			assertEquals(10, maxAge);

		} catch (JSONException e1) {
			e1.printStackTrace();
			fail();
		}

		// Have to check the Cookies also. I expect to have one cookie at least
		Set<String> cookieNames = res.getCookieSettings().getNames();

		// must have at least one cookie
		assertTrue(cookieNames.size() > 0);

		Iterator<String> it = cookieNames.iterator();

		while (it.hasNext()) {
			// I'm assuming only one cookie in the response!! I have no idea if
			// it's the right cookie.
			CookieSetting cookie = res.getCookieSettings().getFirst(it.next());
			// Has to have a name
			assertNotNull(cookie.getName());
			System.out.println("-->" + cookie.getName());

			// Has to have a value
			String authToken = cookie.getValue();
			assertNotNull(authToken);
			System.out.println("-->" + authToken);

			// Has to have a value Max Age
			int maxAge = cookie.getMaxAge();
			// (I've send in the req 10)
			assertEquals(10, maxAge);
			System.out.println("-->" + maxAge);

			// Has to have a Domain
			String domain = cookie.getDomain();
			assertNotNull(domain);
			System.out.println("-->" + domain);
			// ok it's the cookie

			// now decode the cookie value into a valid AuthenticationToken json
			// obj
			byte[] data = Base64.decode(authToken);

			assertNotNull(data);
			String dataAsString = new String(data);
			assertNotNull(dataAsString);
			System.out.println("-->" + dataAsString);

			// Should be able to get a JsonObject from the decoded string :)
			try {
				JSONObject jsonObj = new JSONObject(dataAsString);
				assertNotNull(jsonObj
						.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_ALFRESCO_TICKET));
				assertNotNull(jsonObj
						.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_USERNAME));
				assertNotNull(jsonObj
						.getString(SharedConstants.Json.AUTHENTICATION_TOKEN_MAX_AGE));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}

		}

		/*
		 * try {
		 * 
		 * Object obj = JSONObject.stringToValue(res.getEntityAsText());
		 * 
		 * JsonRepresentation rep = new JsonRepresentation(obj.toString());
		 * JSONObject jsonO = rep.getJsonObject();
		 * 
		 * 
		 * // TODO: Define error messages in JSON Format
		 * assertNotNull(jsonO.get("alfresco_ticket"));
		 * System.out.println("Authentication Tike:" +
		 * jsonO.get("alfresco_ticket"));
		 * 
		 * } catch (Throwable e) { e.printStackTrace(); // TODO Auto-generated
		 * catch block fail(); }
		 */
	}

	@Test
	public void testAuthorizationFailBadCredentials() {

		// Prepare the request
		Request request = new Request(Method.POST, super.serviceUrl
				+ authenticationTokenPath+"/teste");

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
				+ authenticationTokenPath+"/teste");

		// Add client authnetication
		// ChallengeScheme challengeSchema = ChallengeScheme.HTTP_COOKIE;

		// ChallengeResponse challengeResponse = new ChallengeResponse();

		Client client = new Client(Protocol.HTTP);

		Response res = client.handle(request);
		Assert.assertNotNull(res);
		Assert.assertEquals(Status.CLIENT_ERROR_FORBIDDEN, res.getStatus());
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
