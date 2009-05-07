package org.nideasystems.scrumr.restlayer;

import org.nideasystems.scrumr.restlayer.security.ISecurityManager;
import org.restlet.data.CookieSetting;

public class CookieManager implements ICookieManager {
	private final Configuration config = Configuration.get();
	private ISecurityManager securityManager= null;

	public ISecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setSecurityManager(ISecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public Configuration getConfig() {
		return config;
	}

	public CookieSetting createAuthenticationCookieSetting(int maxAge) {

		
		CookieSetting cook = new CookieSetting();
		cook.setDomain(config.getDomainName());
		cook.setName(securityManager.getCoockieName());
		// TODO: Change the version algorithm
		cook.setVersion(config.getMajorVersion() + config.getMinorVersion());
		cook.setMaxAge(maxAge);
		return cook;
	}

}
