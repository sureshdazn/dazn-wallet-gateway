package com.dazn.data.model;

import java.math.BigDecimal;

public class WalletInfo {
	
	private String daznId;
	private BigDecimal daznOttBalance;
	private BigDecimal daznBetBalance;
	
	public String getDaznId() {
		return daznId;
	}
	
	public void setDaznId(String daznId) {
		this.daznId = daznId;
	}
	
	public BigDecimal getDaznOttBalance() {
		return daznOttBalance;
	}
	
	public void setDaznOttBalance(BigDecimal daznOttBalance) {
		this.daznOttBalance = daznOttBalance;
	}
	
	public BigDecimal getDaznBetBalance() {
		return daznBetBalance;
	}
	
	public void setDaznBetBalance(BigDecimal daznBetBalance) {
		this.daznBetBalance = daznBetBalance;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("WalletInfo [daznId=");
		builder.append(daznId);
		builder.append(", daznOttBalance=");
		builder.append(daznOttBalance);
		builder.append(", daznBetBalance=");
		builder.append(daznBetBalance);
		builder.append("]");
		
		return builder.toString();
	
	}
	
}
