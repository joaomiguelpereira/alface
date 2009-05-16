package org.nideasystems.scrumr.webclient.client.service;

import org.nideasystems.scrumr.webclient.client.manager.ClientManager;
import org.nideasystems.scrumr.webclient.client.manager.SecurityManager;
import org.restlet.gwt.Callback;
import org.restlet.gwt.Client;
import org.restlet.gwt.data.CookieSetting;
import org.restlet.gwt.data.Form;
import org.restlet.gwt.data.Protocol;
import org.restlet.gwt.data.Reference;
import org.restlet.gwt.data.Request;
import org.restlet.gwt.data.Response;
import org.restlet.gwt.data.Status;
import org.restlet.gwt.resource.JsonRepresentation;
import org.restlet.gwt.resource.Representation;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 * This is the client connector to underlying services
 * 
 * @author jpereira
 * 
 */
public class AlfrescoUserService extends AbstractAlfrescoService {
	private static AlfrescoUserService instance = null;
	private static final String secret = "6a4b76193f7271638030d16f55bb8d57";
	private AlfrescoUserService() {

	}

	public static AlfrescoUserService getInstance() {
		if (instance == null) {
			instance = new AlfrescoUserService();
		}
		return instance;
	}

	public void authenticate(String userName, String password,
			boolean acceptCookie, int tokenMaxAgeDays) {

		// Create the form
		Form loginForm = new Form();
		loginForm.add("username", userName);
		loginForm.add("password", password);
		// I want to get also the authentication cookie

		loginForm.add("cookie", Boolean.toString(acceptCookie));
		// I also want to set the time of validity of the token/cookie (now just
		// cookie is working)
		loginForm.add("maxAge", Integer.toString(tokenMaxAgeDays));

		Representation repEnt = loginForm.getWebRepresentation();

		// Create a client connector
		Client client = new Client(Protocol.HTTP);

		// Let the connector send the request and we handle the response
		Reference resourceRef = new Reference(SERVICE_URL
				+ AUTHENTICATION_TOKEN_PATH + "/"+secret);

		try {
			client.post(resourceRef, repEnt, new LoginCallBack());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Window.alert(e.getMessage());
		}

	}

	private class LoginCallBack implements Callback {

		public void onEvent(Request request, Response response) {

			AuthenticationToken token = null;
			
			// Check if response is error
			if (response == null) {
				ClientManager.getInstance().handleError("Response was null");
			} else if (!response.getStatus().equals(Status.SUCCESS_OK)) {
				// handle error
				ClientManager.getInstance().handleError(response);
			} else {
				// Handle the authentication
				if (response.getEntity() != null) {
					
					token = getAuthenticationTokenFromEntity(response
							.getEntityAsJson());
					
					SecurityManager.getInstance().authenticate(token);
					ClientManager.getInstance().updateUserStatus();
					ClientManager.getInstance().closeLoginWindow();

					// Check cookies
					if (token.getClientAcceptCookies()) {
						// Assiming only one cookie
						CookieSetting authCookie = response.getCookieSettings()
								.get(0);
						if (authCookie != null) {
							//Window.alert(authCookie.getDomain());
							//Window.alert(authCookie.getName());
							//Window.alert(authCookie.getPath());
							//Window.alert(authCookie.getValue());
							// Set the cookie in browser?

						}
					}

				}

			}

		}

		private AuthenticationToken getAuthenticationTokenFromEntity(
				JsonRepresentation entity) {

			AuthenticationToken token = new AuthenticationToken();
			JSONValue value = entity.getValue();
			JSONObject jsonObj = value.isObject();

			String alfTick = jsonObj.get("alfrescoAuthenticationTicket").isString().stringValue();
			
			token.setAlfrescoTicket(alfTick);

			String userName = jsonObj.get("username").isString().stringValue();
			int maxAgeDays = (int) jsonObj.get("maxAge").isNumber().doubleValue();
			
			boolean clientAcceptCookies = jsonObj.get("acceptCookie").isBoolean().booleanValue();
			token.setClientAcceptCookies(clientAcceptCookies);
			token.setMaxAgeDays(maxAgeDays);
			token.setUserName(userName);
			return token;
		}

	}

	public void logout() {
		// Create a client connector
		Client client = new Client(Protocol.HTTP);

		// Let Resource Reference for the object to delete
		Reference resourceRef = new Reference(SERVICE_URL
				+ AUTHENTICATION_TOKEN_PATH+"/"+secret+SecurityManager.getInstance().getAuthenticationToken().getAlfrescoTicket());
		// send a DELETE for resourec AuthenticationToken
		client.delete(resourceRef, new LogoutCallBack());

	}

	private class LogoutCallBack implements Callback {

		public void onEvent(Request request, Response response) {			
			if (response == null) {
				ClientManager.getInstance().handleError("Response was null");
			} else if (!response.getStatus().equals(Status.SUCCESS_OK)) {
				// handle error
				ClientManager.getInstance().handleError(response);
			}  else {
				
				//remove any session data in client
				//TODO:delete any cookies for this app
				SecurityManager.getInstance().setIsAuthenticated(false);
				SecurityManager.getInstance().setAuthenticationToken(null);
				
				ClientManager.getInstance().getMainToolbar().updateButtons();

			}

		}

	}
}
