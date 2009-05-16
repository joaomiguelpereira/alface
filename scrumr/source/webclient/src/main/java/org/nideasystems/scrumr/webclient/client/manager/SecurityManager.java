package org.nideasystems.scrumr.webclient.client.manager;

import org.nideasystems.scrumr.webclient.client.service.AlfrescoUserService;
import org.nideasystems.scrumr.webclient.client.service.AuthenticationToken;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class SecurityManager {
	private static SecurityManager instance = null;

	private AuthenticationToken authenticationToken = null;
	private Boolean isAuthenticated = false;

	// singleton
	private SecurityManager() {

	}

	public static SecurityManager getInstance() {
		if (instance == null) {
			instance = new SecurityManager();
		}
		return instance;
	}

	public void authenticate(AuthenticationToken token) {

		if (token != null && token.getAlfrescoTicket() != null
				&& token.getClientAcceptCookies() != null
				&& token.getMaxAgeDays() != null && token.getUserName() != null) {
			setAuthenticationToken(token);
			setIsAuthenticated(true);
		}
		

	}

	public void setAuthenticationToken(AuthenticationToken authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public AuthenticationToken getAuthenticationToken() {
		return authenticationToken;
	}

	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void logout() {
		
		//Get ApplicationUserService
		AlfrescoUserService alfUserService = AlfrescoUserService.getInstance();
		//Fail safe with this call. It's not important at the serve right now
		alfUserService.logout();
		
	}

	
}
