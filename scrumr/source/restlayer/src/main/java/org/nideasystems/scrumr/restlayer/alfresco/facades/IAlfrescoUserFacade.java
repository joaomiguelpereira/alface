package org.nideasystems.scrumr.restlayer.alfresco.facades;

import org.json.JSONException;
import org.nideasystems.scrumr.restlayer.alfresco.facades.impl.AlfrescoUserFacadeException;
import org.restlet.data.Protocol;

public interface IAlfrescoUserFacade extends IAlfrescoFacade{

	boolean authenticate(String identifier, String secretAsString, Protocol protocol) throws AlfrescoUserFacadeException;


	/**
	 * Delete a given Authorization ticket
	 * @param ticket The ricket to destroy
	 */
	public void deleteAuthorizationTicket(String ticket);

	/**
	 * Validates a ticket
	 * @param ticket The ticket to validade
	 * @return true if the ticket is valid. false otherwise
	 */
	public boolean isTicketValid(String ticket);


	public String getAlfrescoTicket();
}
