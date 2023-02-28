package com.dazn.data.model;

public class WalletInitiationRequest {
	
	private String daznId;
	private String idToken;
	
	public String getDaznId() {
		return daznId;
	}
	
	public void setDaznId(String daznId) {
		this.daznId = daznId;
	}
	
	public String getIdToken() {
		return idToken;
	}
	
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("WalletInitiationRequest [daznId=");
		builder.append(daznId);
		builder.append(", idToken=");
		builder.append(idToken);
		builder.append("]");
		
		return builder.toString();
	
	}
	
	

}
