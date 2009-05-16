package org.nideasystems.scrumr.alfresco.services;


import org.restlet.data.Status;

public class ServiceException extends Exception{
	
	private Status httpStatus = null;
	private String message = null;
	
	public ServiceException(String string) {
		httpStatus = Status.SERVER_ERROR_INTERNAL;
		message = "Internal Server Error";
	}

	public ServiceException(String string, Status status) {
		this.setMessage(string);
		this.httpStatus = status;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 310590281691210165L;

	public Status getHttpStatus() {
		
		return this.httpStatus;
	}


	public void setHttpStatus(Status httpStatus) {
		this.httpStatus = httpStatus;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getMessage() {
		return message;
	}

}
