package org.nideasystems.web20.poc.weblayer.facades;

import org.json.JSONException;
import org.restlet.data.Protocol;

public interface IAlfrescoUserFacade extends IAlfrescoFacade{

	boolean authenticate(String identifier, String secretAsString, Protocol protocol) throws JSONException;

}
