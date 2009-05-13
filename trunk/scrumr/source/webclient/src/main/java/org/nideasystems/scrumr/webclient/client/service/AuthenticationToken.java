package org.nideasystems.scrumr.webclient.client.service;

public class AuthenticationToken {
	
	private String userName = null;
	private Boolean clientAcceptCookies = false;
	private Integer maxAgeDays = 30;
	private String AlfrescoTicket = null;
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setClientAcceptCookies(Boolean clientAcceptCookies) {
		this.clientAcceptCookies = clientAcceptCookies;
	}
	public Boolean getClientAcceptCookies() {
		return clientAcceptCookies;
	}
	public void setMaxAgeDays(Integer maxAgeDays) {
		this.maxAgeDays = maxAgeDays;
	}
	public Integer getMaxAgeDays() {
		return maxAgeDays;
	}
	public void setAlfrescoTicket(String alfrescoTicket) {
		AlfrescoTicket = alfrescoTicket;
	}
	public String getAlfrescoTicket() {
		return AlfrescoTicket;
	}
	
	

}
