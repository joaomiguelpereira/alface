package org.nideasystems.scrumr.alfresco.services.impl;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.nideasystems.scrumr.alfresco.application.AlfrescoServiceProviderConfiguration;
import org.nideasystems.scrumr.alfresco.services.AbstractAlfrescoService;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;
import org.restlet.data.Reference;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

/**
 * 
 * @author jpereira
 * 
 */
public class AlfrescoUserServiceImpl extends AbstractAlfrescoService implements
		IAlfrescoUserService {

	private static final Logger log = Logger
			.getLogger(AlfrescoUserServiceImpl.class.getName());

	/**
	 * This service maintains during it's lifecycle a ticket a the last
	 * authenticated user
	 */
	private String alfrescoTicket = null;

	/**
	 * Public constructor
	 * 
	 * @param alfrescoServiceConfiguration
	 */
	public AlfrescoUserServiceImpl(
			AlfrescoServiceProviderConfiguration alfrescoServiceConfiguration) {
		// Calls calls super
		super(alfrescoServiceConfiguration);

	}

	/**
	 * Delete an authenticationTicke
	 */
	public void deleteAuthenticationTicket(String ticket) {
		// TODO Auto-generated method stub

	}

	/**
	 * Check with alfresco server if a given tiket is valid
	 */
	public boolean isTicketValid(String ticket) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Given a username/password, try to authenticate in Alfresco
	 * 
	 * @return true if authentication went ok, false otherwise
	 * @throws AlfrescoUserServiceException
	 *             if something go wrong, for example the Alfresco server is
	 *             unreachable
	 */

	public boolean authenticate(String identifier, String secretAsString)
			throws AlfrescoUserServiceException {

		boolean retVal = false;

		// Hold the JSONRepresentation of login credentials
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("username", identifier);
			jsonObject.put("password", secretAsString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.fatal("error creating JSON Object", e);
			throw new AlfrescoUserServiceException(e);
		}

		// Create a JSON representation
		Representation userCredentials = new JsonRepresentation(jsonObject);

		// Create a reference to Alfresco Authentication API
		Reference ref = new Reference(getConfiguration()
				.getAlfrescoAuthenticationServiceUri());

		// POST the Representation to Alfresco
		Response response = client.post(ref, userCredentials);

		if (response != null) {
			//for debug purposes
			dumpResponse(response,log);
			
			// If Alfresco returned SUCCESS_OK...
			if (response.getStatus().equals(Status.SUCCESS_OK)) {

				// Update return
				retVal = true;

				// Set current alfrescoTicket
				this.alfrescoTicket = getAlfrescoTicketAsString(response);

			} else if (response.getStatus().equals(
					Status.CLIENT_ERROR_NOT_FOUND)) {
				// Throw an exception if Alfresco is not found
				throw new AlfrescoUserServiceException(
						"Alfresco Service Not found.");
			} else {
				log
						.warn("Could not authenticate a user in Alfresco Server. Returned Status "
								+ response.getStatus().getName());
			}
		} else {
			log.warn("Got a null response from POST to Alfresco");
			throw new AlfrescoUserServiceException(
					"The call to Alfresco returned null");
		}

		return retVal;
	}

	/**
	 * From a representation, extract the ticket
	 * The current contract is:
	 * {data:{ticket:ticketvalue}}
	 * @param response
	 * @return
	 * @throws AlfrescoUserServiceException 
	 */
	private String getAlfrescoTicketAsString(Response response) throws AlfrescoUserServiceException {
		String returnvalue = null;
		String res = response.getEntityAsText();
		JSONTokener jsonTokener = new JSONTokener(res);
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonTokener);
			JSONObject data = jsonObj.getJSONObject("data");
			returnvalue = data.getString("ticket");
		} catch (JSONException e) {
			log.fatal("error retrieving Ticket Data from Alfresco response",e);
			throw new AlfrescoUserServiceException(e);
		}

		return returnvalue;

	}

	/**
	 * Getter for Alfresco ticket
	 * @return
	 */
	public String getAlfrescoTicket() {
		return alfrescoTicket;
	}

}
