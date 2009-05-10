package org.nideasystems.scrumr.restlayer.util;

import org.nideasystems.scrumr.restlayer.Configuration;
import org.restlet.data.CookieSetting;

public class BasicCookieUtils {

	public static CookieSetting createAuthenticationCookieSetting(
			String cookieName, int maxAge) {
		Configuration config = Configuration.get();
		CookieSetting cook = new CookieSetting();
		cook.setDomain(config.getDomainName());

		cook.setName(cookieName);
		// TODO: Change the version algorithm
		cook.setVersion(config.getMajorVersion() + config.getMinorVersion());
		cook.setMaxAge(maxAge);
		return cook;
	}
}
