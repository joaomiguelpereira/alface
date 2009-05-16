package org.nideasystems.scrumr.alfresco.application;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class AlfrescoServiceProviderConfiguration {

	/** The Logger **/

	private static final Logger log = Logger
			.getLogger(AlfrescoServiceProviderConfiguration.class.getName());

	/** The only instance */
	private static AlfrescoServiceProviderConfiguration instance = null;

	/** Constants **/
	private final String PROPS_NAME = "alfresco.properties";
	private final String ALFRESCO_SERVICE_BASE_URI_KEY = "alfresco.service.base.uri";
	private final String ALFRESCO_AUTHENTICATION_SERVICE_URI_KEY = "alfresco.authentication.service.uri";
	
	
	

	/** Fields **/
	private String alfrescoServiceBaseUri = null;
	private String alfrescoAuthenticationServiceUri = null;

	/**
	 * Private Constructor.
	 */
	private AlfrescoServiceProviderConfiguration() {

	}

	/**
	 * Get a copy of the configuration. To each thread that want to use this
	 * configuration is given a copy of the original Configuration
	 * 
	 * @return A copy of the original Configuration
	 */
	public static AlfrescoServiceProviderConfiguration get()
			throws AlfrescoServiceProviderInitializationException {

		if (instance == null) {
			instance = new AlfrescoServiceProviderConfiguration();
			try {
				instance.read();
			} catch (ConfigurationException e) {
				log.fatal("Could not read the configuration"
						+ e.getLocalizedMessage());
				throw new AlfrescoServiceProviderInitializationException(e);
			}
		}

		// Create a copy
		AlfrescoServiceProviderConfiguration copy = null;

		try {
			copy = (AlfrescoServiceProviderConfiguration) instance.clone();
		} catch (CloneNotSupportedException e) {
			log.debug("Could not Clone the Object" + e.getLocalizedMessage());
			throw new AlfrescoServiceProviderInitializationException(e);
		}
		// AlfrescoServiceProviderConfiguration.copy(instance,copy);
		return copy;

	}

	@Override
	public int hashCode() {
		return 37
				* (this.getAlfrescoAuthenticationServiceUri() != null ? this
						.getAlfrescoAuthenticationServiceUri().hashCode() : 0)
				+ 12
				* (this.getAlfrescoServiceBaseUri() != null ? this
						.getAlfrescoServiceBaseUri().hashCode() : 0);
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof AlfrescoServiceProviderConfiguration) {
			AlfrescoServiceProviderConfiguration conf = (AlfrescoServiceProviderConfiguration) obj;
			if (conf.getAlfrescoAuthenticationServiceUri().equals(
					this.getAlfrescoAuthenticationServiceUri())
					&& conf.getAlfrescoServiceBaseUri().equals(
							this.getAlfrescoServiceBaseUri())) {
				equals = true;
			}
		}

		return equals;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		AlfrescoServiceProviderConfiguration dest = new AlfrescoServiceProviderConfiguration();
		dest.setAlfrescoAuthenticationServiceUri(this
				.getAlfrescoAuthenticationServiceUri());
		dest.setAlfrescoServiceBaseUri(this.getAlfrescoServiceBaseUri());
		return dest;

	}

	/**
	 * Read the Configuration from the PROPS_NAME.
	 * 
	 * @throws ConfigurationException
	 */
	public void read() throws ConfigurationException {
		// Read props

		PropertiesConfiguration config = new PropertiesConfiguration(PROPS_NAME);
		this.alfrescoServiceBaseUri = config
				.getString(ALFRESCO_SERVICE_BASE_URI_KEY);
		this.alfrescoAuthenticationServiceUri = this.alfrescoServiceBaseUri
				+ "/"
				+ config.getString(ALFRESCO_AUTHENTICATION_SERVICE_URI_KEY);

	}

	/**
	 * @param alfrescoAuthenticationServiceUri
	 */
	public void setAlfrescoAuthenticationServiceUri(
			String alfrescoAuthenticationServiceUri) {
		this.alfrescoAuthenticationServiceUri = alfrescoAuthenticationServiceUri;
	}

	/**
	 * 
	 * @param alfrescoServiceBaseUri
	 */
	public void setAlfrescoServiceBaseUri(String alfrescoServiceBaseUri) {
		this.alfrescoServiceBaseUri = alfrescoServiceBaseUri;
	}

	/**
	 * @return the alfrescoServiceBaseUri
	 */
	public String getAlfrescoServiceBaseUri() {
		return alfrescoServiceBaseUri;
	}

	/**
	 * @return the alfrescoAuthenticationServiceUri
	 */
	public String getAlfrescoAuthenticationServiceUri() {
		return alfrescoAuthenticationServiceUri;
	}
}
