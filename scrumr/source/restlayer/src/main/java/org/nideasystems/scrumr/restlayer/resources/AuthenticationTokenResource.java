package org.nideasystems.scrumr.restlayer.resources;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;
import org.nideasystems.scrumr.alfresco.services.impl.AlfrescoUserServiceException;
import org.nideasystems.scrumr.common.SharedConstants;
import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.nideasystems.scrumr.restlayer.Configuration;

import org.nideasystems.scrumr.restlayer.util.BasicCookieUtils;
import org.nideasystems.scrumr.security.ISecurityServiceProvider;
import org.nideasystems.scrumr.security.services.IBasicSecurityService;
import org.nideasystems.scrumr.security.services.ISecurityService;

import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

/**
 * Resource AuthenticationToken This Authentication Token represent a valid
 * login
 * 
 * @author jpereira
 * 
 */
public class AuthenticationTokenResource extends BaseResource {

	private Logger log = Logger.getLogger(AuthenticationTokenResource.class.getName());

	private AlfrescoApplication app = getAlfrescoApplication();
	private IAlfrescoServiceProvider alfServiceProvider = app.getServerApp()
			.getServiceProvider(IAlfrescoServiceProvider.class);
	private IBasicSecurityService securityService = app.getServerApp()
			.getServiceProvider(ISecurityServiceProvider.class)
			.getBasicSecurityService();

	private IAlfrescoUserService userService = alfServiceProvider.getUserService();
	private String secret = null;
	
	@Override
	protected void doInit() throws ResourceException {

		//Get the secret
		secret = (String)getRequestAttributes().get("secret");
		log.debug("The secret is :"+secret);
		super.doInit();

	}

	@Delete
	public Representation delete() {
		
		JsonRepresentation returnRepresentation = null;
		//Move this code
		//To logout, the secret is secret+alfrescoicekt_to_delete
		if (this.secret!= null) {
			if (!this.secret.startsWith(securityService.getHashFromValue(ISecurityService.SECRET))) {
				getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
				return super.createJSonError("The secret is invalid", null,Status.CLIENT_ERROR_FORBIDDEN.getName());
			}
		} else {
			getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
			return super.createJSonError("Did you forgot to send the Secret?", null,Status.CLIENT_ERROR_FORBIDDEN.getName());	
		}
		
		
		//Now, if I have the secret as secret+alfrescoticket
		String alfrescoTicket = this.secret.substring(securityService.getHashFromValue(ISecurityService.SECRET).length());
		
		//Now call alfresco service
		try {
			userService.deleteAuthenticationTicket(alfrescoTicket);
		} catch (AlfrescoUserServiceException e) {
			getResponse().setStatus(e.getHttpStatus());
			returnRepresentation = super.ecapsulateExceptionJson(e);
			
		}
		
		return new JsonRepresentation("ok");
	}

	/**
	 * Create or Replace a AuthenticationTokeb based on a Username/password
	 * 
	 * @return JsonRepresentation with a valid token or a JsonRepresentation
	 *         with an error
	 */
	@Post("json")
	public Representation authenticate() {
		
		//check the presence of the secret
		if (this.secret != null) {
			if ( !secret.equals(securityService.getHashFromValue(ISecurityService.SECRET)) ) {				
				getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
				return super.createJSonError("The secret is invalid", null,Status.CLIENT_ERROR_FORBIDDEN.getName());
			}
		} else {
			getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
			return super.createJSonError("Did you forgot to send the Secret?", null,Status.CLIENT_ERROR_FORBIDDEN.getName());
		}
		String username = null;
		String password = null;
		//String key = null;
		JsonRepresentation representation = null;

		int maxAge = Configuration.get().getAuthenticationCookieDefaultMaxAge(); // default
		// seconds
		boolean addCookie = true; // Default, send also the cookie for client
		// ---Get the values from the submited form
		Form form = getRequestEntityAsForm();

		password = form.getValues("username");
		username = form.getValues("password");

		

		try {
			maxAge = new Integer(form.getValues("maxAge"));
		} catch (NumberFormatException e1) {
			log.warn(e1.getLocalizedMessage(), e1);
			// leave the default
		}
		try {
			addCookie = new Boolean(form.getValues("cookie"));
		} catch (Exception e1) {
			log.warn(e1.getLocalizedMessage(), e1);
			// leave the default
		}

		// Create a new JSON Object to POST in Alfresco
		JSONObject jsonObject = new JSONObject();

		// ICookieManager cookieMgr = app.getCoockieManager();

		

		try {
			// Call facade to authenticate the user in Alfresco
			
			try {
				userService.authenticate(username, password);
			} catch (Exception e) {
				getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
				return super.createJSonError("The secret is invalid", null,Status.CLIENT_ERROR_FORBIDDEN.getName());
			}


				// TODO: Move the code that create and set the cookie out out of
				// the resource represenation creation
				// Use it in the authentication filter...??
				// The cookie value is encoded as base64 with the jsonObject
				// string

				// TODO: Organize Magic Values
				jsonObject
						.append(
								SharedConstants.Json.AUTHENTICATION_TOKEN_ALFRESCO_TICKET,
								userService.getAlfrescoTicket());
				jsonObject.append(
						SharedConstants.Json.AUTHENTICATION_TOKEN_MAX_AGE,
						maxAge);

				jsonObject.append(
						SharedConstants.Json.AUTHENTICATION_TOKEN_USERNAME,
						username);

				jsonObject
						.append(
								SharedConstants.Json.AUTHENTICATION_TOKEN_ACCEPT_COOKIE,
								addCookie);

				representation = new JsonRepresentation(jsonObject);

				// if cookie is required
				if (addCookie) {
					String jsonAsString = jsonObject.toString();

					String name = securityService
							.getHashFromValue("AuthenticationCookieSetting");

					CookieSetting cookieSetting = BasicCookieUtils
							.createAuthenticationCookieSetting(name, maxAge);
					cookieSetting.setValue(securityService
							.getValueTo64BasedEncoded(jsonAsString));

					// Add the cookie
					getResponse().getCookieSettings().add(cookieSetting);

				}

				getResponse().setStatus(Status.SUCCESS_OK);


		} catch (Exception e) {
			representation = ecapsulateExceptionJson(e);
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);

		}

		return representation;

	}

}
