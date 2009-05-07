package org.nideasystems.scrumr.webclient.client.service;

import org.restlet.gwt.Callback;
import org.restlet.gwt.Client;
import org.restlet.gwt.data.Protocol;
import org.restlet.gwt.data.Reference;
import org.restlet.gwt.data.Request;
import org.restlet.gwt.data.Response;

import com.google.gwt.user.client.Window;

/**
 * This is the client connector to underlying services
 * @author jpereira
 *
 */
public class AlfrescoUserService {
	private  static AlfrescoUserService instance = null;
	
	
	private AlfrescoUserService() {
		
	}
	public static AlfrescoUserService getInstance() {
		if ( instance == null ) {
			instance = new AlfrescoUserService();
		}
		return instance;
	}
	
	
	public void authenticate(String userName, String password) {
		
		Client client = new Client(Protocol.HTTP);

		//Send the query
		userName = "username";
		password = "pass";
		try {
			Reference resourceRef = new Reference("http://localhost:8082/user/authenticationToken?userName="+userName+"&password="+password);
			
			client.get(resourceRef, new LoginCallBack());
		} catch (Exception e) {// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

	private class LoginCallBack implements Callback {

		public void onEvent(Request request, Response response) {
			
			Window.alert(response.getEntity().getText());
			
		}
		
	}
}
