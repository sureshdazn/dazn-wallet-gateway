package com.dazn.data.model;

public class UserInfo {
	
	private String daznId;
	private String daznBetId;
	
	public String getDaznId() {
		return daznId;
	}
	
	public void setDaznId(String daznId) {
		this.daznId = daznId;
	}
	
	public String getDaznBetId() {
		return daznBetId;
	}
	
	public void setDaznBetId(String daznBetId) {
		this.daznBetId = daznBetId;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("UserInfo [daznId=");
		builder.append(daznId);
		builder.append(", daznBetId=");
		builder.append(daznBetId);
		builder.append("]");
		
		return builder.toString();
	
	}
	
}
