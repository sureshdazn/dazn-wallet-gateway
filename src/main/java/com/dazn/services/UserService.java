package com.dazn.services;

import org.springframework.stereotype.Service;

import com.dazn.cache.ICache;
import com.dazn.data.model.UserInfo;
import com.dazn.data.repositories.DynamoRepository;

@Service
public class UserService {
	
	public UserInfo getUserInfo(String daznId, DynamoRepository dynamoRepository, ICache cache) {
		return null;
	}
	
	public UserInfo setUserInfo(String daznId, DynamoRepository dynamoRepository, ICache cache) {
		return null;
	}
	
	public boolean deleteUserInfo(String daznId, DynamoRepository dynamoRepository, ICache cache) {
		return false;
	}
	
}
