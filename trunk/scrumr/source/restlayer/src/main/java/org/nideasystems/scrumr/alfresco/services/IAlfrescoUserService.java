package org.nideasystems.scrumr.alfresco.services;


import org.nideasystems.scrumr.alfresco.services.impl.AlfrescoUserServiceException;
import org.restlet.data.Protocol;

public interface IAlfrescoUserService extends IAlfrescoService {

	public void authenticate(String identifier, String secretAsString) throws AlfrescoUserServiceException;
	
	/**
	 * Delete a given Authorization ticket in alfresco
	 * @param ticket The ticket to destroy
	 */
	public void deleteAuthenticationTicket(String ticket) throws AlfrescoUserServiceException;

	/**
	 * Validates a ticket with Alfresco
	 * @param ticket The ticket to validate
	 * @return true if the ticket is valid. false otherwise
	 */
	public void isTicketValid(String ticket) throws AlfrescoUserServiceException;

	/**
	 * Get the last valid ticket retrieved from Alfresco within this thread
	 * @return A String representation of Alfresco Ticket
	 */
	public String getAlfrescoTicket();
}
