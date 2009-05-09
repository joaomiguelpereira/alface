package org.nideasystems.scrumr.serverapp;

public interface IServerApplication{

	<T extends IServiceProvider> void addServiceProvider(Class<T> serviceClazz, IServiceProvider newAlfrescoServiceProvider); 

	<T extends IServiceProvider> T getServiceProvider(Class<T> clazz);

	
}
