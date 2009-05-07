package org.nideasystems.scrumr.restlayer.security;

public class SecurityManagerImpl implements ISecurityManager {


	
	public String getCoockieName() {
		return "Cooockei";
		
	}

	
	
	public String createCookieValue(String alfrescoTicket) {
		//TODO: Check algorithm to code this
		return alfrescoTicket;
	}

}
