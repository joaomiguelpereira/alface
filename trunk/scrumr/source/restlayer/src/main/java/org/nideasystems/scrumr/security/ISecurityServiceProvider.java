package org.nideasystems.scrumr.security;

import org.nideasystems.scrumr.security.services.IBasicSecurityService;
import org.nideasystems.scrumr.serverapp.IServiceProvider;

public interface ISecurityServiceProvider extends IServiceProvider{

	public IBasicSecurityService getBasicSecurityService();
	
}
