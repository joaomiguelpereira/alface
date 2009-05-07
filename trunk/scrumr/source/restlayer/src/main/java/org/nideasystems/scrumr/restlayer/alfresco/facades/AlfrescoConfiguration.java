package org.nideasystems.scrumr.restlayer.alfresco.facades;



import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class AlfrescoConfiguration {

	
	private final String PROPS_NAME = "alfresco.properties";
	private final String ALFRESCO_SERVICE_BASE_URI_KEY = "alfresco.service.base.uri";
	private final String ALFRESCO_AUTHENTICATION_SERVICE_URI_KEY = "alfresco.authentication.service.uri";
	private final String ALFRESCO_SERVICE_BASE_URI_TEST_BROKEN_KEY = "alfresco.service.base.uri.test.broken";
	
	
	
	private String alfrescoServiceBaseUri = null;
	private String alfrescoAuthenticationServiceUri = null;
	
	/**For testing purpose only TODO:Make a better design for this*/
	private String alfrescoServiceBaseUriTestBroken = null;
	
	public void read() throws ConfigurationException {
		//Read props
		
		PropertiesConfiguration config = new PropertiesConfiguration(PROPS_NAME);
		
		this.alfrescoServiceBaseUri = config.getString(ALFRESCO_SERVICE_BASE_URI_KEY);
		this.alfrescoAuthenticationServiceUri = this.alfrescoServiceBaseUri+"/"+config.getString(ALFRESCO_AUTHENTICATION_SERVICE_URI_KEY);
		this.setAlfrescoServiceBaseUriTestBroken(config.getString(ALFRESCO_SERVICE_BASE_URI_TEST_BROKEN_KEY));
		
	}
	public void setAlfrescoAuthenticationServiceUri(
			String alfrescoAuthenticationServiceUri) {
		this.alfrescoAuthenticationServiceUri = alfrescoAuthenticationServiceUri;
	}
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
	public void setAlfrescoServiceBaseUriTestBroken(
			String alfrescoServiceBaseUriTestBroken) {
		this.alfrescoServiceBaseUriTestBroken = alfrescoServiceBaseUriTestBroken;
	}
	public String getAlfrescoServiceBaseUriTestBroken() {
		return alfrescoServiceBaseUriTestBroken;
	}

}
