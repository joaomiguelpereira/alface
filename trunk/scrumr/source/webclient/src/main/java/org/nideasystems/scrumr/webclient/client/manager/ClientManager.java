package org.nideasystems.scrumr.webclient.client.manager;

import org.nideasystems.scrumr.webclient.client.LoginWindow;
import org.nideasystems.scrumr.webclient.client.MainToolbar;



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
	
}
