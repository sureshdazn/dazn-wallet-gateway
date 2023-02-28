package com.dazn.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.dazn.data.db.model.UserDBObj;
import com.dazn.data.db.model.WalletDBObj;

@Repository
public class DynamoRepository {
	
	@Autowired
    private DynamoDBMapper dynamoDBMapper;
	
	public UserDBObj saveUserInfo(UserDBObj userDBObj) {
		
		dynamoDBMapper.save(userDBObj);
		return userDBObj;
		
	}
	
	public UserDBObj getUserInfoById(String daznId) {
		return dynamoDBMapper.load(UserDBObj.class, daznId);
	}
	
	public WalletDBObj saveWalletInfo(WalletDBObj walletDBObj) {
		
		dynamoDBMapper.save(walletDBObj);
		return walletDBObj;
		
	}
	
	public WalletDBObj getUserWalletInfoById(String daznId) {
		return dynamoDBMapper.load(WalletDBObj.class, daznId);
	}

	
}
