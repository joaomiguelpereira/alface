package org.nideasystems.scrumr.restlayer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class Configuration {
	
	private static final Logger log = Logger.getLogger(Configuration.class);
	private static Configuration instance = null;
	
	private final String PROPS_NAME = "application.properties";
	
	private final String DOMAIN_NAME_KEY = "application.domain";
	private final String MAJOR_VERSION_KEY = "application.major_version";
	private final String MINOR_VERSION_KEY = "application.minor_version";
	private final String AUTHENTICATION_COOKIE_DEFAULT_MAX_AGE_KEY = "authentication.cookie.default.max.age";
	

	private String domainName = null;
	
	private int minorVersion = 0;
	private int majorVersion = 0;
	private int authenticationCookieDefaultMaxAge = 0;
	
	
	public void read() throws ConfigurationException {
		PropertiesConfiguration configuration = new PropertiesConfiguration(PROPS_NAME);
		
		this.setDomainName(configuration.getString(DOMAIN_NAME_KEY));
		this.setMajorVersion(configuration.getInt(MAJOR_VERSION_KEY));
		this.setMinorVersion(configuration.getInt(MINOR_VERSION_KEY));
		this.setAuthenticationCookieDefaultMaxAge( configuration.getInt(AUTHENTICATION_COOKIE_DEFAULT_MAX_AGE_KEY) );
		
		
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainName() {
		return domainName;
	}
	
	private Configuration() {
		
	}
	
	public static Configuration get() {
		if (instance == null ) {
			instance = new Configuration();
			try {
				instance.read();
			} catch (ConfigurationException e) {
				throw new RuntimeException(e);
			}
		}
		//Create a copy of the instance
		Configuration dest = null;
		
		try {
			dest = (Configuration)instance.clone();
		} catch (CloneNotSupportedException e) {
			//TODO: Finish 
			log.fatal("error while clonig configuration");
			throw new RuntimeException("error while clonig configuration");
			
		}
		
		return dest;
	}

	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Configuration config = new Configuration();
		config.setAuthenticationCookieDefaultMaxAge(this.getAuthenticationCookieDefaultMaxAge());
		config.setDomainName(this.getDomainName());
		config.setMajorVersion(this.getMajorVersion());
		config.setMinorVersion(this.getMinorVersion());
		return config;
	}

	@Override
	public boolean equals(Object obj) {
		boolean retValue = false;
		if (obj instanceof Configuration ) {
			Configuration theConfig = (Configuration)obj;
			if ( (theConfig.getAuthenticationCookieDefaultMaxAge() == this.getAuthenticationCookieDefaultMaxAge()) &&
					theConfig.getDomainName().equals(this.getDomainName()) && 
					theConfig.getMajorVersion()==this.getMajorVersion()&&
					theConfig.getMinorVersion()== this.getMinorVersion()) {
				retValue = true;
			}
		}
		return retValue;
	}

	@Override
	public int hashCode() {
		int hash = this.getAuthenticationCookieDefaultMaxAge()+this.getDomainName().hashCode()+this.getMajorVersion()+this.getMinorVersion();
		return hash;
	}


	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getAuthenticationCookieDefaultMaxAge() {
		return this.authenticationCookieDefaultMaxAge;
		
	}

	public void setAuthenticationCookieDefaultMaxAge(
			int authenticationCookieDefaultMaxAge) {
		this.authenticationCookieDefaultMaxAge = authenticationCookieDefaultMaxAge;
	}

	

}
