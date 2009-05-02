package org.nideasystems.scrumr.restlayer;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.nideasystems.scrumr.restlayer.alfresco.facades.IAlfrescoFacadeManager;
import org.restlet.Component;

import org.restlet.data.Protocol;

@Ignore
public class TestBase extends TestCase{

	protected Integer serverPort = 8182;
	protected String serviceUrl = "http://localhost:" + serverPort + "/service";
	private Component component = null;
	protected IAlfrescoFacadeManager facadeManager;

	@Ignore
	protected void setUp() {
		AlfrescoApplication app = new AlfrescoApplication();
		// Initialize the server
		// Create a new Component.
		component = new Component();

		// Add a new HTTP server listening on port 8182.
		component.getServers().add(Protocol.HTTP, 8182);

		// Attach the sample application.
		component.getDefaultHost().attach("/", app);

		// Start the component.
		try {
			component.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.facadeManager = app.getFacadeManager();

	}

	@Ignore
	protected void brakeAlfrescoServer() {
		this.facadeManager.getConfiguration().setAlfrescoAuthenticationServiceUri("----BROKE------");
		
		
	}
	
	@Ignore
	protected void tearDown() {
		if (this.component != null) {
			try {
				this.component.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
