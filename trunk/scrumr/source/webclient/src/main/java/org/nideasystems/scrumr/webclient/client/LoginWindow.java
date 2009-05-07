package org.nideasystems.scrumr.webclient.client;

import org.nideasystems.scrumr.webclient.client.service.AlfrescoUserService;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;

import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;

import com.gwtext.client.widgets.form.TextField;

import com.gwtext.client.widgets.layout.FitLayout;

public class LoginWindow {

	public interface Constants {
		public final String WINDOW_TITLE = "Your Login details";

		public final Integer WIDTH = 330;
		public final Integer MIN_WIDTH = 300;
		public final Integer MIN_HEIGHT = 300;
		public final Integer HEIGHT = 150;
		public final Integer PADDINGS = 5;

		public final String USER_NAME_LABEL = "User Name";
		public final String USER_NAME_LABEL_NAME = "username";

		public final String PASSWORD_LABEL = "Password";
		public final String PASSWORD_LABEL_NAME = "password";

		public final String OK_BUTTON_LABEL = "Login";
		public final String OK_BUTTON_NAME = "loginButon";

		public final String CANCEL_BUTTON_LABEL = "Cancel";

		public final String EMPTY_TEXT_ERROR = "This field cannot be null";

	}

	private static LoginWindow instance = null;
	private Window window = null;

	// private FormPanel formPanel = null;

	public static LoginWindow getInstance() {
		if (instance == null)
			instance = new LoginWindow();
		return instance;

	}

	private Window createWindow() {
		Window window = new Window();
		window = new Window();
		window.setTitle(Constants.WINDOW_TITLE);

		window.setWidth(Constants.WIDTH);
		window.setHeight(Constants.HEIGHT);
		window.setClosable(false);
		window.setResizable(false);

		window.setMinWidth(Constants.MIN_WIDTH);

		window.setLayout(new FitLayout());
		window.setPaddings(Constants.PADDINGS);
		window.setButtonAlign(Position.CENTER);

		// Build buttons

		ButtonListenerAdapter loginButtonListener = new ButtonListenerAdapter() {

			@Override
			public void onClick(Button button, EventObject e) {
				com.google.gwt.user.client.Window.alert("Clicked OK");
				AlfrescoUserService.getInstance()
						.authenticate("teste", "teste");
			}

		};

		ButtonListenerAdapter cancelLoginButtonListener = new ButtonListenerAdapter() {

			@Override
			public void onClick(Button button, EventObject e) {
				LoginWindow.getInstance().close();
			}

		};

		window.addButton(new Button(Constants.OK_BUTTON_LABEL,
				loginButtonListener));
		window.addButton(new Button(Constants.CANCEL_BUTTON_LABEL,
				cancelLoginButtonListener));

		window.setCloseAction(Window.HIDE);
		window.setPlain(true);

		return window;
	}

	/**
	 * TODO: Find out how to override the text in the error
	 * 
	 * @return the username TextField
	 */
	private TextField createUserNameField() {
		TextField txtField = new TextField(Constants.USER_NAME_LABEL,
				Constants.USER_NAME_LABEL_NAME);
		txtField.setAllowBlank(false);
		txtField.setFieldMsgTarget("under");
		txtField.setTabIndex(0);
		// txtField.set (Constants.EMPTY_TEXT_ERROR);
		return txtField;
	}

	/**
	 * TODO: Find out how to override the text in the error and to to set the
	 * password field behavior (asterix)
	 * 
	 * @return the password TextField
	 */
	private TextField createPasswordField() {
		TextField txtField = new TextField(Constants.PASSWORD_LABEL,
				Constants.PASSWORD_LABEL_NAME);
		txtField.setAllowBlank(false);
		txtField.setFieldMsgTarget("under");
		// txtField.setEmptyText(Constants.EMPTY_TEXT_ERROR);
		txtField.setTabIndex(0);
		return txtField;
	}

	private FormPanel createFormPanel() {
		FormPanel formPanel = new FormPanel();

		formPanel = new FormPanel();
		// strips all Ext styling for the component
		formPanel.setBaseCls("x-plain");
		formPanel.setLabelWidth(120);
		formPanel.setTrackResetOnLoad(true);

		// formPanel.setUrl("http://localhost:8082/");

		formPanel.setWidth(500);
		formPanel.setHeight(300);

		// anchor width by percentage
		formPanel.add(createUserNameField());

		// anchor width by percentage
		formPanel.add(createPasswordField());

		return formPanel;

	}

	public LoginWindow() {
		this.window = createWindow();
		this.window.add(createFormPanel());
	}

	public void close() {
		window.close();
	}

	public void show() {
		window.show();
	}
}
