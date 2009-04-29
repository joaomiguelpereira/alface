package org.nideasystems.scrumr.restlayer.facades;

import org.json.JSONException;
import org.restlet.data.Protocol;

public interface IAlfrescoUserFacade extends IAlfrescoFacade{

	boolean authenticate(String identifier, String secretAsString, Protocol protocol) throws JSONException;

}
