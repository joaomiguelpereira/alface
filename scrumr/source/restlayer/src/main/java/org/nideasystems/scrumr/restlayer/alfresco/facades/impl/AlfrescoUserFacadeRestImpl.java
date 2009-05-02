package org.nideasystems.scrumr.restlayer.alfresco.facades.impl;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.nideasystems.scrumr.restlayer.alfresco.facades.AlfrescoConfiguration;
import org.nideasystems.scrumr.restlayer.alfresco.facades.AbstractAlfrescoFacade;
import org.nideasystems.scrumr.restlayer.alfresco.facades.IAlfrescoUserFacade;

import org.restlet.Client;


import org.restlet.data.Protocol;
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
public class AlfrescoUserFacadeRestImpl extends AbstractAlfrescoFacade implements IAlfrescoUserFacade {

	
	private static final Logger log = Logger.getLogger(AlfrescoUserFacadeRestImpl.class.getName());
	
	public AlfrescoUserFacadeRestImpl(AlfrescoConfiguration applicationConfiguration) {
		super(applicationConfiguration);

	}



	public void deleteAuthorizationTicket(String ticket) {
		// TODO Auto-generated method stub
		
	}



	public String getAuthorizationTicket(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean isTicketValid(String ticket) {
		// TODO Auto-generated method stub
		return false;
	}




	

	public boolean authenticate(String identifier, String secretAsString,
			Protocol protocol) throws JSONException {
		
		boolean retVal = false;
		Client client = new Client(protocol);
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("username", identifier);
		jsonObject.put("password", secretAsString);
		
		
		
		Representation userCredentials = new JsonRepresentation(jsonObject);
		
		Reference ref = new Reference(getConfiguration().getAlfrescoAuthenticationServiceUri());
		
		Response response = client.post(ref,userCredentials);
		
		
		if ( response != null ) {
			log.debug("Response Entity As text: "+response.getEntityAsText());
			log.debug("Response Status: "+response.getStatus().getName());
			if ( response.getStatus().equals(Status.SUCCESS_OK) ) {
				retVal = true;
			} else {
				log.warn("Could not authenticate user in Alfresco Server. Returned Status "+response.getStatus().getName());
			}
		} else {
			log.warn("Got a null response from POST to Alfresco");
		}
		
		return retVal;
	}

}
