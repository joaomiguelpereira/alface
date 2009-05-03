package org.nideasystems.scrumr.restlayer;

import org.apache.log4j.Logger;
import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Request;
import org.restlet.data.Response;

import org.restlet.security.ChallengeAuthenticator;

public class ApplicationAuthenticator extends ChallengeAuthenticator{

	public ApplicationAuthenticator(Context context, boolean optional,
			ChallengeScheme challengeScheme, String realm) {
		super(context, optional, challengeScheme, realm);
		
	}
	



	@Override
	protected boolean authenticate(Request request, Response response) {
		log.debug("Authenticating....");
		
		return false;
		
		//return super.authenticate(request, response);
	}

	private final static Logger log = Logger.getLogger(ApplicationAuthenticator.class.getName());
	

}
