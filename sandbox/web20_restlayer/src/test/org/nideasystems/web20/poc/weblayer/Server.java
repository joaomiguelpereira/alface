package org.nideasystems.web20.poc.weblayer;


import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;



public class Server extends Application {

	public static void main(String... args) throws Exception {
		 // Create a new Component.
        Component component = new Component();

        // Add a new HTTP server listening on port 8182.
        component.getServers().add(Protocol.HTTP, 8182);

        // Attach the sample application.
        component.getDefaultHost().attach("/",
                new AlfrescoApplication());

        // Start the component.
        component.start();
    }


}
