package org.nideasystems.scrumr.alfresco.application;

import org.restlet.data.Reference;
import org.restlet.data.Response;
import org.restlet.representation.Representation;

public interface IAlfrescoRestClient {
	
	public Response post(Reference reference, Representation representation);

}
