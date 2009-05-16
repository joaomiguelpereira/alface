package org.nideasystems.scrumr.restlayer.resources;



import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.nideasystems.scrumr.alfresco.services.ServiceException;
import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.nideasystems.scrumr.restlayer.Configuration;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ServerResource;

public abstract class BaseResource extends ServerResource{

	private Configuration configuration = null;
	/**
	 * Get the Alfresco Application
	 * @return
	 */
	protected AlfrescoApplication getAlfrescoApplication() {
		return (AlfrescoApplication)getApplication();
	}
	
	protected JsonRepresentation createJSonError(String message, String details, String status) {
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.append("errorMsg", message);
			jsonObject.append("serverDate", new Date());
			jsonObject.append("status", status);
			jsonObject.append("details", details);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonRepresentation(jsonObject);
		
	}
	/**
	 * TODO: Add correct date, Remove magic
	 * @param ex
	 * @return
	 */
	protected JsonRepresentation ecapsulateExceptionJson(ServiceException ex) {
		return createJSonError(ex.getLocalizedMessage(), null, (ex.getHttpStatus() != null? ex.getHttpStatus().getName():Status.CLIENT_ERROR_BAD_REQUEST.getName()) );
	}
	
	protected JsonRepresentation ecapsulateExceptionJson(Exception ex) {
		return createJSonError(ex.getLocalizedMessage(), null, Status.CLIENT_ERROR_BAD_REQUEST.getName()) ;
	}
	
	
	
}
