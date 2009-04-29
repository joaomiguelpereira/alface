package org.nideasystems.web20.poc.client;



import org.restlet.gwt.Callback;
import org.restlet.gwt.Client;
import org.restlet.gwt.data.Protocol;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ScrumrApplication implements EntryPoint {

	
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the ScrumR application.");
	private Anchor signInLink = new Anchor("Sign In");

	
	private void loadMainWindow() {
		
		// create the main panel and assign it a BorderLayout
		Panel panel = new Panel();
		panel.setBorder(false);
		panel.setPaddings(15);
		panel.setLayout(new FitLayout());

		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());

		// add north panel
		Panel northPanel = new Panel();
		northPanel.setHtml("<p>north panel</p>");
		northPanel.setHeight(32);
		northPanel.setBodyStyle("background-color:#FFFF88");
		borderPanel.add(northPanel, new BorderLayoutData(RegionPosition.NORTH));

		// add south panel
		Panel southPanel = new Panel();
		southPanel.setHtml("<p>south panel</p>");
		southPanel.setHeight(100);
		southPanel.setBodyStyle("background-color:#CDEB8B");
		southPanel.setCollapsible(true);
		southPanel.setTitle("South");

		BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);
		southData.setMinSize(100);
		southData.setMaxSize(200);
		southData.setMargins(new Margins(0, 0, 0, 0));
		southData.setSplit(true);
		borderPanel.add(southPanel, southData);

		// add east panel
		Panel eastPanel = new Panel();
		eastPanel.setHtml("<p>east panel</p>");
		eastPanel.setTitle("East Side");
		eastPanel.setCollapsible(true);
		eastPanel.setWidth(225);
		eastPanel.setCollapsed(true);

		BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);
		eastData.setSplit(true);
		eastData.setMinSize(175);
		eastData.setMaxSize(400);
		eastData.setMargins(new Margins(0, 0, 5, 0));

		borderPanel.add(eastPanel, eastData);

		// This pannel should be an accordion pannel

		Panel westPanel = new Panel();
		westPanel.setLayout(new AccordionLayout(true));

		westPanel.setHtml("<p>west panel</p>");
		westPanel.setTitle("Company name");
		westPanel.setBodyStyle("background-color:#EEEEEE");
		westPanel.setCollapsible(true);
		westPanel.setWidth(200);

		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(true);
		westData.setMinSize(175);
		westData.setMaxSize(400);
		westData.setMargins(new Margins(0, 5, 0, 0));

		borderPanel.add(westPanel, westData);

		Panel centerPanel = new Panel();
		centerPanel.setHtml("<p>center panel</p>");
		centerPanel.setBodyStyle("background-color:#C3D9FF");

		centerPanel.add(buidlButtonTestRestApi());
		borderPanel.add(centerPanel,
				new BorderLayoutData(RegionPosition.CENTER));

		panel.add(borderPanel);

		Viewport viewport = new Viewport(panel);
	}

	private Button buidlButtonTestRestApi() {
		Button button = new Button("Test", new ButtonListenerAdapter() {

			@Override
			public void onClick(Button button, EventObject e) {
				String url = "http://localhost:8082/service/user/authenticate?username=joao&password=joao";

				
				Client client = new Client(Protocol.HTTP);
				
				client.get(url, new Callback() {

					@Override
					public void onEvent(org.restlet.gwt.data.Request arg0,
							org.restlet.gwt.data.Response arg1) {
						Window.alert("--"+arg1.getEntity().getText());
						
					}
					
				});
				/*
				client.get(url, new Callback() {
					@Override
					public void onEvent(org.restlet.data.Request request) {
						
					}
				});*/

				// org.restlet.data.Response response = new
				// Client(Protocol.HTTP).get(url);

//				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
//						URL.encode(url));
//				builder.setRequestData("application/json");
//				//				
//				try {
//					//					
//					builder.sendRequest(null, new MyRequestCallback());
//				} catch (RequestException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				//				
				//				
			}

		});

		return button;
	}

	private class MyRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			Window.alert("on error");

		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			Window.alert("on response:" + response.getText());

		}

	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		loadMainWindow();
	}
}
