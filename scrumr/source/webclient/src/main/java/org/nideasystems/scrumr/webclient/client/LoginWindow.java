package org.nideasystems.scrumr.webclient.client;

import org.nideasystems.scrumr.webclient.client.manager.ClientManager;
import org.nideasystems.scrumr.webclient.client.service.AlfrescoUserService;


import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;


public class LoginWindow extends Window {

	/**
	 * Constants for the LoginWindow
	 * @author jpereira
	 *
	 */
	public interface Constants {
		public final String WINDOW_TITLE = "Your Login details";

		public final Integer WIDTH = 350;
		public final Integer MIN_WIDTH = 300;
		public final Integer MIN_HEIGHT = 300;
		public final Integer HEIGHT = 200;
		public final Integer PADDINGS = 5;

		public final String USER_NAME_LABEL = "User Name";
		public final String USER_NAME_LABEL_NAME = "username";

		public final String PASSWORD_LABEL = "Password";
		public final String PASSWORD_LABEL_NAME = "password";

		public final String OK_BUTTON_LABEL = "Login";
		public final String OK_BUTTON_NAME = "loginButon";

		public final String CANCEL_BUTTON_LABEL = "Cancel";

		public final String EMPTY_TEXT_ERROR = "This field cannot be null";

		public final String COOKIE_LABEL_NAME = "acceptACookie";

		public final String COOKIE_LABEL = "Accept a cookie?";

	}

	/**
	 * There's only one instance of the login window
	 */
	
	//The delegate window
	//private Window window = null;
	
	private TextField userNameTf = null;
	private TextField passwordTf = null;
	private Checkbox cookieCheck = null;
	private ComboBox maxAgeCb = null;
	private Button cancelBtn = null;
	private Button loginBtn = null;
	 
	



	

	/**
	 * Create the delegate window
	 * @return
	 */
	private void createWindow() {
		//Window window = new Window();
		//window = new Window();
		super.setIconCls("wdnPannelHeader");
		
		super.setTitle(Constants.WINDOW_TITLE);

		super.setWidth(Constants.WIDTH);
		super.setHeight(Constants.HEIGHT);
		super.setClosable(false);
		super.setResizable(false);

		super.setMinWidth(Constants.MIN_WIDTH);

		super.setLayout(new FitLayout());
		super.setPaddings(Constants.PADDINGS);
		super.setButtonAlign(Position.CENTER);
		
		//Add img 
		
		
		
		// create the ButtonListenetys
		ButtonListenerAdapter loginButtonListener = new ButtonListenerAdapter() {

			@Override
			public void onClick(Button button, EventObject e) {
				//Try to Authenticate
				AlfrescoUserService.getInstance()
						.authenticate(userNameTf.getText(), passwordTf.getText(), cookieCheck.getValue(), Integer.valueOf(maxAgeCb.getValueAsString()));
			}
		};
		ButtonListenerAdapter cancelLoginButtonListener = new ButtonListenerAdapter() {

			@Override
			public void onClick(Button button, EventObject e) {
				ClientManager.getInstance().getLoginWindow().close();
			}

		};

		//Add the buttons
		super.addButton(this.loginBtn = new Button(Constants.OK_BUTTON_LABEL,
				loginButtonListener));
		super.addButton(this.cancelBtn = new Button(Constants.CANCEL_BUTTON_LABEL,
				cancelLoginButtonListener));

		//TODO:wtf
		super.setCloseAction(Window.HIDE);
		super.setPlain(true);
	}

	/**
	 * TODO: Find out how to override the text in the error
	 * 
	 * @return the username TextField
	 */
	private TextField createUserNameField() {
		userNameTf = new TextField(Constants.USER_NAME_LABEL,
				Constants.USER_NAME_LABEL_NAME);
		userNameTf.setAllowBlank(false);
		userNameTf.setFieldMsgTarget("under");
		userNameTf.setTabIndex(0);
		// txtField.set (Constants.EMPTY_TEXT_ERROR);
		return userNameTf;
	}

	/**
	 * TODO: Find out how to override the text in the error and to to set the
	 * password field behavior (asterix)
	 * 
	 * @return the password TextField
	 */
	private TextField createPasswordField() {
		passwordTf = new TextField(Constants.PASSWORD_LABEL,
				Constants.PASSWORD_LABEL_NAME);
		passwordTf.setAllowBlank(false);
		passwordTf.setFieldMsgTarget("under");
		// txtField.setEmptyText(Constants.EMPTY_TEXT_ERROR);
		passwordTf.setTabIndex(0);
		return passwordTf;
	}
	
	private Checkbox createAcceptACookie()  {
		cookieCheck = new Checkbox(Constants.COOKIE_LABEL, Constants.COOKIE_LABEL_NAME);
		cookieCheck.setChecked(true);
		return cookieCheck;
	}
	
	private String[][] getSeconds() {
		String[][] retTable = new String[30][2];
		for (int x=1; x<31; x++) {
			retTable[x-1][0] =""+x;
			retTable[x-1][1] =""+(x*86400);
		}
		return retTable;
		
		
	}
	private ComboBox createMaxAgeDaysCb() {
		final Store daysStore = new SimpleStore(new String[] {"day","seconds"},getSeconds());
		daysStore.load();
		
		this.maxAgeCb = new ComboBox();
		
		
		this.maxAgeCb.setFieldLabel("Days to remain logged in");
		this.maxAgeCb.setStore(daysStore);
		this.maxAgeCb.setDisplayField("day");
		this.maxAgeCb.setValueField("seconds");
		
		
		this.maxAgeCb.setMode(ComboBox.LOCAL); 
		
		//this.maxAgeCb.setTriggerAction(ComboBox.ALL);
		
		//this.maxAgeCb.select(30,true);
		this.maxAgeCb.setAutoHeight(true);
		this.maxAgeCb.setValue("15");
		this.maxAgeCb.setEditable(false);
		
		
		//this.maxAgeCb.setAllowBlank(false);
		
		
		//this.maxAgeCb.setLoadingText("Loading...");  
		//this.maxAgeCb.setTypeAhead(false);  
		this.maxAgeCb.setSelectOnFocus(true);  
		this.maxAgeCb.setWidth(120);
		
		
		
		this.maxAgeCb.setHideTrigger(false);
		
		
		
		return this.maxAgeCb;
	}
	

	private FormPanel createFormPanel() {
		
 
		FormPanel formPanel = new FormPanel(Position.LEFT);
		
		// strips all Ext styling for the component
		formPanel.setBaseCls("x-plain");
		formPanel.setFrame(true);
		
		formPanel.setLabelWidth(150);
		formPanel.setWidth(500);
		formPanel.setHeight(300);
		
		formPanel.setTrackResetOnLoad(true);
		
		
		// 
		formPanel.add(createUserNameField());
		// 
		formPanel.add(createPasswordField());
		// 
		formPanel.add(createAcceptACookie());
		// 
		formPanel.add(createMaxAgeDaysCb());


	

		return formPanel;

	}

	public LoginWindow() {
		super();
		createWindow();
		super.add(createFormPanel());
	}

	/**
	 * This will destroy the window
	 */
	public void close() {
		super.setVisible(false);
		//super.close();
	}

	public void show() {
		super.show();
			
	}

	public void setMaxAgeCb(ComboBox maxAgeCb) {
		this.maxAgeCb = maxAgeCb;
	}

	public ComboBox getMaxAgeCb() {
		return maxAgeCb;
	}
}
