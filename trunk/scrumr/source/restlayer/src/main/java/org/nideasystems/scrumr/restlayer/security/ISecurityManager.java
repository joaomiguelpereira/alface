package org.nideasystems.scrumr.restlayer.security;

public interface ISecurityManager {

	String getCoockieName();

	

	String createCookieValue(String alfrescoTicket);

}
