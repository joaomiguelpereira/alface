package org.nideasystems.scrumr.webclient.client.manager;

import org.nideasystems.scrumr.webclient.client.LoginWindow;
import org.nideasystems.scrumr.webclient.client.MainToolbar;
import org.restlet.gwt.data.MediaType;
import org.restlet.gwt.data.Response;
import org.restlet.gwt.resource.JsonRepresentation;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;



public class ClientManager {

	
	private LoginWindow loginWindow = null;
	private MainToolbar mainToolbar = null;
	
	
	private static ClientManager instance = null;
	//singleton
	private ClientManager() {
		
	}
	public static ClientManager getInstance() {
		if (instance == null ) {
			instance = new ClientManager();	
		}
		return instance;
	}
	
	public void updateUserStatus() {
		//Update the Toolbar
		mainToolbar.updateButtons();	
	}
	public void closeLoginWindow() {
		loginWindow.close();		
	}
	public void setLoginWindow(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}
	public LoginWindow getLoginWindow() {
		return loginWindow;
	}
	public void setMainToolbar(MainToolbar mainToolbar) {
		this.mainToolbar = mainToolbar;
	}
	public MainToolbar getMainToolbar() {
		return mainToolbar;
	}
	public void handleError(String string) {
		Window.alert(string);
		
	}
	public void handleError(Response response) {
		//Try to assume a JSON respresentation in the error
		
		//Check if is 
		
			try {
				JsonRepresentation jsonRepo = response.getEntityAsJson();
				JSONValue jsonValue = jsonRepo.getValue();
				StringBuffer sb = new StringBuffer();
				JSONObject jsonObj = jsonValue.isObject();
				sb.append("Message: "+jsonObj.get("errorMsg"));
				sb.append("\n");
				sb.append("Details: "+jsonObj.get("details"));
				sb.append("\n");
				sb.append("Status: "+jsonObj.get("status"));
				sb.append("\n");
				sb.append("Date: "+jsonObj.get("serverDate"));
				Window.alert(sb.toString());
			} catch (Exception e) {
				Window.alert(response.getEntity().getText());
				
			}
		
			
		
		
		
	}
	
}
