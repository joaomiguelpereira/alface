package org.nideasystems.scrumr.restlayer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Configuration {
	
	private static Configuration instance = null;
	
	private final String PROPS_NAME = "application.properties";
	
	private final String DOMAIN_NAME_KEY = "application.domain";
	private final String MAJOR_VERSION_KEY = "application.major_version";
	
	private final String MINOR_VERSION_KEY = "application.minor_version";

	private String domainName = null;
	
	private int minorVersion = 0;
	private int majorVersion = 0;
	
	public void read() throws ConfigurationException {
		PropertiesConfiguration configuration = new PropertiesConfiguration(PROPS_NAME);
		
		this.setDomainName(configuration.getString(DOMAIN_NAME_KEY));
		this.setMajorVersion(configuration.getInt(MAJOR_VERSION_KEY));
		this.setMinorVersion(configuration.getInt(MINOR_VERSION_KEY));
		
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
		Configuration dest = new Configuration();
		Configuration.copy(instance,dest);
		
		return dest;
	}

	private static void copy(Configuration source, Configuration dest) {
		dest.setDomainName(source.getDomainName());
		dest.setMajorVersion(source.getMajorVersion());

		dest.setMinorVersion(source.getMinorVersion());

		
		
		
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

	

}
