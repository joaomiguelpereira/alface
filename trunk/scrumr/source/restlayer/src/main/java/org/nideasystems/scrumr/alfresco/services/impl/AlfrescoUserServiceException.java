package org.nideasystems.scrumr.alfresco.services.impl;

import org.json.JSONException;
import org.nideasystems.scrumr.alfresco.services.ServiceException;
import org.restlet.data.Status;


//TODO: Fix exceptions to have the message and possible status codes
public class AlfrescoUserServiceException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5463002861281400017L;


	public AlfrescoUserServiceException(String string, Status status) {
		super(string, status);
	}

	public AlfrescoUserServiceException(String string) {
		super(string,Status.SERVER_ERROR_INTERNAL);
	}

	

}
