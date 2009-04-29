package org.nideasystems.web20.poc.weblayer;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.nideasystems.web20.poc.weblayer.facades.IAlfrescoUserFacade;
import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Request;

import org.restlet.security.Guard;

public class ApplicationGuard extends Guard {

	private static final Logger log = Logger.getLogger(ApplicationGuard.class
			.getName());

	public ApplicationGuard(Context context, ChallengeScheme scheme,
			String realm) throws IllegalArgumentException {
		super(context, scheme, realm);

	}

	@Override
	public boolean checkSecret(Request request, String identifier, char[] secret) {

		boolean authenticated = false;
		IAlfrescoUserFacade alfUserFacade = ((AlfrescoApplication)getApplication()).getFacadeManager().getUserFacade();
		 
		getLogger()
				.fine(
						"checkSecret(Request request, String identifier, char[] secret)");
		getLogger().fine(
				"Challenge Identifier:"
						+ request.getChallengeResponse().getIdentifier());
		getLogger().fine("Challenge Credentials:" + new String(secret));

		
		try {
			authenticated = alfUserFacade.authenticate(request.getChallengeResponse().getIdentifier(), getSecretAsString(secret),request.getProtocol());
		} catch (JSONException e) {
			log.fatal("Could not construct the Json?",e);
		}
		return authenticated;
		
		
		
		// Get the remaining part key/val pairs

		
		// return super.checkSecret(request, identifier, secret);
	}
	
	private String getSecretAsString(char[] secret) {
		StringBuffer sb = new StringBuffer();
		for (Character ch:secret) {
			sb.append(ch);
		}
		return sb.toString();
	}

}
