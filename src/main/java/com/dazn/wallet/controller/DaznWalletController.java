package com.dazn.wallet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dazn.cache.CacheFactory;
import com.dazn.cache.ICache;
import com.dazn.data.DaznConstants;
import com.dazn.data.model.UserInfo;
import com.dazn.data.model.WalletInfo;
import com.dazn.data.model.WalletInitiationRequest;
import com.dazn.data.model.WalletInitiationResponse;
import com.dazn.data.repositories.DynamoRepository;
import com.dazn.services.UserService;
import com.dazn.services.WalletService;

@RestController
@RequestMapping("/dazn")
public class DaznWalletController {
	
    private static final Logger logger = LogManager.getLogger(DaznWalletController.class);

	
	 @Autowired
	 Environment env;
	
	 @Autowired
	 DynamoRepository dynamoRepository;
	 
	 @Autowired
	 WalletService walletService;
	 
	 @Autowired
	 UserService userService;
	 
	 public ICache getCache() {
		 
		 CacheFactory cacheFactory = new CacheFactory();
		 return cacheFactory.getCache(env.getProperty(DaznConstants.CACHE_TYPE));
		 
	 }
	
	 @GetMapping("/heartbeat")
	 public String heartBeat() {	
		 return "Dazn wallet gateway is up and running";
	 
	 }
	 
	 @GetMapping("/initiateWallet")
	 public WalletInitiationResponse initiateWallet (WalletInitiationRequest walletInitiationRequest) {	
		 
		 WalletInitiationResponse walletInitiationResponse = walletService.initiateWallet(walletInitiationRequest);
		 		 
		 logger.info("DaznWalletController : initiateWallet : walletInitiationResponse : " 
				 	 + walletInitiationResponse);
		 
		 return walletInitiationResponse;
	 
	 }
	 
	 @PostMapping("/v1/getBalance")
	 @ResponseBody
	 public WalletInfo getBalance(@RequestBody UserInfo userInfo) {
		 return walletService.getBalance(userInfo, dynamoRepository, getCache());
	 }
	 
	 @PostMapping("/v1/updateBalance")
	 @ResponseBody
	 public WalletInfo updateBalance(@RequestBody UserInfo userInfo) {
		 return walletService.updateBalance(userInfo, dynamoRepository, getCache());
	 }
	 
	 @PostMapping("/v1/getUserInfo")
	 public UserInfo getUserInfo(String daznId, DynamoRepository dynamoRepository, ICache cache) {
		 return userService.getUserInfo(daznId, dynamoRepository, cache);
	 }
	 
	 @PostMapping("/v1/setUserInfo")
	 public UserInfo setUserInfo(String daznId, DynamoRepository dynamoRepository, ICache cache) {
		 return userService.setUserInfo(daznId, dynamoRepository, cache);
	 }
	 
	 @DeleteMapping
	 public boolean deleteUserInfo(String daznId, DynamoRepository dynamoRepository, ICache cache) {
			return userService.deleteUserInfo(daznId, dynamoRepository, cache);
	  }

}
