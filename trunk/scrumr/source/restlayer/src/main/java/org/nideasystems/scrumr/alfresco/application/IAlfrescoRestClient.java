package org.nideasystems.scrumr.alfresco.application;

import org.restlet.data.Reference;
import org.restlet.data.Response;
import org.restlet.representation.Representation;

public interface IAlfrescoRestClient {
	
	/**
	 * Delegates the post
	 * @param reference
	 * @param representation
	 * @return
	 */
	public Response post(Reference reference, Representation representation);

	/**
	 * delegates the delete
	 * @param reference
	 */
	public Response delete(Reference reference);
}
