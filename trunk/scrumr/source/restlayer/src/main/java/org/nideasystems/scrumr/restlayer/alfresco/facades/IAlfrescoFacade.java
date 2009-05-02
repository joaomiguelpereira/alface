package org.nideasystems.scrumr.restlayer.alfresco.facades;

public interface IAlfrescoFacade {

	/**
	 * Given a userName and Password, get an autorization ticket.
	 * This ticket is used to make subsequent calls to alfresco repo.
	 * Is the user could not be authenticated, a empty string is returned
	 * @param userName The User name
	 * @param password the Password
	 * @return the ticket in a string format. Empty string if user is not euthorized
	 */
	public String getAuthorizationTicket(String userName, String password);
	
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
}
