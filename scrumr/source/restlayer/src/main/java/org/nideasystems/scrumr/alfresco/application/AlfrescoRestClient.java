package org.nideasystems.scrumr.alfresco.application;

import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.data.Response;
import org.restlet.representation.Representation;

public class AlfrescoRestClient implements IAlfrescoRestClient {
	private static final Protocol PROTOCOL = Protocol.HTTP;
	private Client client = null;
	
	private AlfrescoRestClient() {
		client = new Client(PROTOCOL);
	}

	public Response post(Reference reference, Representation representation) {
		return client.post(reference, representation);
		
	}

	/**Return a instance of an AlfrescoClient*/ 
	public static IAlfrescoRestClient get() {
		
		return new AlfrescoRestClient();
	}

}
