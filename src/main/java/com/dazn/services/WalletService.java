package com.dazn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dazn.cache.ICache;
import com.dazn.data.model.UserInfo;
import com.dazn.data.model.WalletInfo;
import com.dazn.data.model.WalletInitiationRequest;
import com.dazn.data.model.WalletInitiationResponse;
import com.dazn.data.repositories.DynamoRepository;

@Service
public class WalletService {
	
	@Autowired
	RestTemplate restTemplate;

	public WalletInfo getBalance(UserInfo userInfo, DynamoRepository dynamoRepository, ICache cache) {	
			return null;
	}
	
	public WalletInfo updateBalance(UserInfo userInfo, DynamoRepository dynamoRepository, ICache cache) {
			return null;
	}
	
	public WalletInitiationResponse initiateWallet(WalletInitiationRequest walletInitiationRequest) {
		
		ResponseEntity<WalletInitiationResponse> result = restTemplate.getForEntity("https://mocki.io/v1/da95a72b-6343-40f4-b171-6d02b3c9abea", WalletInitiationResponse.class);
		return result.getBody();
		
	}
	
}
