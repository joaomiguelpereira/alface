package org.nideasystems.web20.poc.weblayer.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWorld extends ServerResource {

	
	@Get
	public String doS() {
		return "This is the API Home";
	}
}
