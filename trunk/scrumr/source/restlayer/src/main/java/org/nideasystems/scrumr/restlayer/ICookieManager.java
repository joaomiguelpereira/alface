package org.nideasystems.scrumr.restlayer;


import org.nideasystems.scrumr.restlayer.security.ISecurityManager;
import org.restlet.data.CookieSetting;

/**
 * @deprecated
 * @author jpereira
 *
 */
public interface ICookieManager {

	
	public CookieSetting createAuthenticationCookieSetting(int maxAge);

	public ISecurityManager getSecurityManager();
	
	public void setSecurityManager(ISecurityManager securityManager);
}
