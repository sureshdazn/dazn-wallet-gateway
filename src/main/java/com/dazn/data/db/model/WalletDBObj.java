package com.dazn.data.db.model;

import java.math.BigDecimal;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "WalletInfo")
public class WalletDBObj {
	
	private String daznId;
	private BigDecimal daznOttBalance;
	private BigDecimal daznBetBalance;
	
	@DynamoDBAttribute
	public String getDaznId() {
		return daznId;
	}
	
	public void setDaznId(String daznId) {
		this.daznId = daznId;
	}
	
	@DynamoDBAttribute
	public BigDecimal getDaznOttBalance() {
		return daznOttBalance;
	}
	
	public void setDaznOttBalance(BigDecimal daznOttBalance) {
		this.daznOttBalance = daznOttBalance;
	}
	
	@DynamoDBAttribute
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