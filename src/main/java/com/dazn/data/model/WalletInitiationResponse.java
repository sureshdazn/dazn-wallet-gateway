package com.dazn.data.model;

public class WalletInitiationResponse {
	
	private String userId;
	private String walletId;
	private String walletStatus;
	private String productId;
	private String pamId;
	private String providerId;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getWalletId() {
		return walletId;
	}
	
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	
	public String getWalletStatus() {
		return walletStatus;
	}
	
	public void setWalletStatus(String walletStatus) {
		this.walletStatus = walletStatus;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getPamId() {
		return pamId;
	}
	
	public void setPamId(String pamId) {
		this.pamId = pamId;
	}
	
	public String getProviderId() {
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("WalletInitiationResponse [userId=");
		builder.append(userId);
		builder.append(", walletId=");
		builder.append(walletId);
		builder.append(", walletStatus=");
		builder.append(walletStatus);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", pamId=");
		builder.append(pamId);
		builder.append(", providerId=");
		builder.append(providerId);
		builder.append("]");
		
		return builder.toString();
	
	}
	
}
