package org.nideasystems.scrumr.restlayer.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWorld extends ServerResource {

	
	@Get
	public String doS() {
		return "This is the API Home";
	}
}
