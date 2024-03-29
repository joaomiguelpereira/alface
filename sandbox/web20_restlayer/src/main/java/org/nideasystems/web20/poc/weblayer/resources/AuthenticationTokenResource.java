package org.nideasystems.web20.poc.weblayer.resources;

import java.util.Map;

import org.apache.log4j.Logger;

import org.nideasystems.web20.poc.weblayer.AlfrescoApplication;
import org.nideasystems.web20.poc.weblayer.facades.IFacadeManager;
import org.nideasystems.web20.poc.weblayer.utils.UrlParams;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

public class AuthenticationTokenResource extends BaseResource {

	Logger log = Logger.getLogger(AuthenticationTokenResource.class.getName());
	private String username = "";
	private String password = "";

	@Override
	protected void doInit() throws ResourceException {

		super.doInit();
		
		Map<String, String> map = UrlParams
				.getRemainindPartParams(getReference().getRemainingPart(true));

		if (map.get("username") != null)
			this.username = map.get("userName");

		if (map.get("password") != null)
			this.password = map.get("password");

		log.debug(getReference().getRemainingPart(true, true));

	}

	@Get("html")
	public Representation authenticate() {
		AlfrescoApplication app = getAlfrescoApplication();
		IFacadeManager facadeMgr = app.getFacadeManager();
		facadeMgr.getUserFacade().getAuthorizationTicket(this.username, this.password);
		
		
		/*
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject().put("teste", "teste");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Representation json = new JsonRepresentation(jsonObject);
		*/
		Representation text = new StringRepresentation("testess");
		return text;

	}

}
