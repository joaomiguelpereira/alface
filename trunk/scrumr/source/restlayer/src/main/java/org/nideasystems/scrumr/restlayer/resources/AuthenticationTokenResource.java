package org.nideasystems.scrumr.restlayer.resources;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;
import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.nideasystems.scrumr.restlayer.Configuration;
import org.nideasystems.scrumr.restlayer.ICookieManager;
import org.nideasystems.scrumr.restlayer.security.ISecurityManager;
import org.nideasystems.scrumr.restlayer.util.BasicCookieUtils;
import org.nideasystems.scrumr.security.ISecurityServiceProvider;
import org.nideasystems.scrumr.security.services.IBasicSecurityService;
import org.nideasystems.scrumr.security.services.ISecurityService;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

/**
 * Resource AuthenticationToken
 * This Authentication Token represent a valid login
 * @author jpereira
 *
 */
public class AuthenticationTokenResource extends BaseResource {

	Logger log = Logger.getLogger(AuthenticationTokenResource.class.getName());
	

	@Override
	protected void doInit() throws ResourceException {

		super.doInit();		

	}

	
	/**
	 * Create or Replace a AuthenticationTokeb based on a Username/password
	 * @return JsonRepresentation with a valid token or a JsonRepresentation with an error
	 */
	@Post("json")
	public Representation authenticate() {
		String username = null;
		String password = null;
		//---Get the values from the submited form
		Form form = getRequestEntityAsForm();
		
		
		password = form.getValues("username");
		username = form.getValues("password");
		
		
		//Create a new JSON Object to POST in Alfresco
		JSONObject jsonObject = new JSONObject();
		JsonRepresentation representation = null;

		AlfrescoApplication app = getAlfrescoApplication();
		IAlfrescoServiceProvider alfServiceProvider = app.getServerApp().getServiceProvider(IAlfrescoServiceProvider.class);
		IBasicSecurityService securityService = app.getServerApp().getServiceProvider(ISecurityServiceProvider.class).getBasicSecurityService();
		
		
		IAlfrescoUserService userService = alfServiceProvider.getUserService();

		
		//ICookieManager cookieMgr = app.getCoockieManager();
		

		boolean authenticated = false;

		try {
			//Call facade to authenticate the user in Alfresco
			authenticated = userService.authenticate(username,
					password);
			
			// Set the authentication cookie
			if (authenticated) {
		
				//TODO: Move the code that create and set the cookie out out of the resource represenation creation
				//Use it in the authentication filter...??
				String name = securityService.getHashFromValue("AuthenticationCookieSetting");
				Configuration.get().getAuthenticationCookieDefaultMaxAge();
				CookieSetting cookieSetting = BasicCookieUtils.createAuthenticationCookieSetting(name,Configuration.get().getAuthenticationCookieDefaultMaxAge());
				 
				cookieSetting.setValue(securityService.getValueTo64BasedEncoded(userService.getAlfrescoTicket()));
				
				//Add the cookie
				getResponse().getCookieSettings().add(cookieSetting);

				//TODO: Organize Magic Values
				jsonObject.append("alfresco_ticket", alfServiceProvider.getUserService()
						.getAlfrescoTicket());
				jsonObject.append("authentication_cookie_max_age", 12);
				representation = new JsonRepresentation(jsonObject);
				getResponse().setStatus(Status.SUCCESS_OK);

			} else {
				jsonObject.accumulate("error", "Could not authenticate");
				
				//jsonObject.append("error", "Could not authenticate");
				representation = new JsonRepresentation(jsonObject);
				//getResponse().setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
				getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
			}

		} catch (Exception e ) {

			representation = new JsonRepresentation(ecapsulateExceptionJson(e));
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);

		}

		return representation;

	}

}
