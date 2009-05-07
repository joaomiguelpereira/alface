package org.nideasystems.scrumr.restlayer.resources;



import org.json.JSONException;
import org.json.JSONObject;
import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.nideasystems.scrumr.restlayer.Configuration;
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
	
	/**
	 * TODO: Add correct date, Remove magic
	 * @param ex
	 * @return
	 */
	protected JSONObject ecapsulateExceptionJson(Exception ex) {
		JSONObject jsObject =  new JSONObject();
		try {
			jsObject.append("errorMsg", ex.getLocalizedMessage());
			jsObject.append("serverDate", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return jsObject;
		
	}
}
