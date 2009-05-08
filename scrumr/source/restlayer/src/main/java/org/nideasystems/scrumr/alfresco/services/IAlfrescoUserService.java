package org.nideasystems.scrumr.alfresco.services;


import org.nideasystems.scrumr.alfresco.services.impl.AlfrescoUserServiceException;
import org.restlet.data.Protocol;

public interface IAlfrescoUserService extends IAlfrescoService {

	boolean authenticate(String identifier, String secretAsString) throws AlfrescoUserServiceException;
	
	/**
	 * Delete a given Authorization ticket in alfresco
	 * @param ticket The ticket to destroy
	 */
	public void deleteAuthenticationTicket(String ticket);

	/**
	 * Validates a ticket with Alfresco
	 * @param ticket The ticket to validate
	 * @return true if the ticket is valid. false otherwise
	 */
	public boolean isTicketValid(String ticket);

	/**
	 * Get the last valid ticket retrieved from Alfresco within this thread
	 * @return A String representation of Alfresco Ticket
	 */
	public String getAlfrescoTicket();
}
