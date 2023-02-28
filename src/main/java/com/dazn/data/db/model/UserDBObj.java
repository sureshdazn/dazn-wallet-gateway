package com.dazn.data.db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "PlayerInfo")
public class UserDBObj {
	
	private String daznId;
	private String daznBetId;
	
	@DynamoDBAttribute
	public String getDaznId() {
		return daznId;
	}

	@DynamoDBAttribute
	public String getDaznBetId() {
		return daznBetId;
	}

	public void setDaznId(String daznId) {
		this.daznId = daznId;
	}

	public void setDaznBetId(String daznBetId) {
		this.daznBetId = daznBetId;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("PlayerInfo [daznId=");
		builder.append(daznId);
		builder.append(", daznBetId=");
		builder.append(daznBetId);
		builder.append("]");
		
		return builder.toString();
		
	}
	
}
