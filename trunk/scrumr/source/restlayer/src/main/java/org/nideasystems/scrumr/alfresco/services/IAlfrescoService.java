package org.nideasystems.scrumr.alfresco.services;


import org.nideasystems.scrumr.alfresco.application.IAlfrescoRestClient;

public interface IAlfrescoService {
	
	public void setAlfrescoClient(IAlfrescoRestClient client);
	public IAlfrescoRestClient getAlfrescoClient();

	
}
