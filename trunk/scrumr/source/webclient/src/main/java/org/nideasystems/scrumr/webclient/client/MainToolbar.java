package org.nideasystems.scrumr.webclient.client;

import org.nideasystems.scrumr.webclient.client.manager.ClientManager;
import org.nideasystems.scrumr.webclient.client.manager.SecurityManager;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;


import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class MainToolbar extends Toolbar {


	//The buttons in the toolbar
	ToolbarButton loginButton = null;

	ToolbarButton userInfoButton = null;
	
	//Exists only one instance of the toolbar
	public MainToolbar() {
		super();
		//Set up the toolbar here
		super.addFill();
		super.setHeight(30);
		createButtons();
	}
	
	
	
	private void createButtons() {
		this.loginButton = new ToolbarButton();
		//Create the login
		this.loginButton.setText("Login");
		this.loginButton.setVisible(!SecurityManager.getInstance().getIsAuthenticated());
		this.loginButton.addListener(new ButtonListenerAdapter() {

			@Override
			public void onClick(Button button, EventObject e) {
				ClientManager.getInstance().getLoginWindow().show();
			}
			
		});
		super.addButton(loginButton);
		
		//create the userInfo button/Menu
		userInfoButton = new ToolbarButton();
		
		this.userInfoButton.setVisible(SecurityManager.getInstance().getIsAuthenticated());
		if ( SecurityManager.getInstance().getIsAuthenticated()) {
			this.userInfoButton.setText("Welcome "+SecurityManager.getInstance().getAuthenticationToken().getUserName());
		}
		
		super.addButton(userInfoButton);
	}
	public void updateButtons() {
		//hide/show login button
		this.loginButton.setVisible(!SecurityManager.getInstance().getIsAuthenticated());
		
		if ( SecurityManager.getInstance().getIsAuthenticated() ) {
			this.userInfoButton.setText("Welcome "+SecurityManager.getInstance().getAuthenticationToken().getUserName());
			this.userInfoButton.setVisible(true);
		}
	}
	
	
}
