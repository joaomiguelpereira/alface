package org.nideasystems.scrumr.restlayer.alfresco.facades.impl;

import org.json.JSONException;

//TODO: Fix exceptions to have the message and possible status codes
public class AlfrescoUserFacadeException extends Exception {

	public AlfrescoUserFacadeException(JSONException e) {
		// TODO Auto-generated constructor stub
	}
	

	public AlfrescoUserFacadeException(String string) {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 310590281691210165L;

}
