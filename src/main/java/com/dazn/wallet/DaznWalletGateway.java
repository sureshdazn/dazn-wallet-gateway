package com.dazn.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.dazn.cache.CacheFactory;
import com.dazn.cache.ICache;
import com.dazn.config.DaznConfigParams;
import com.dazn.data.DaznConstants;

@SpringBootApplication
@ComponentScan("com.dazn")
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class DaznWalletGateway implements CommandLineRunner {
	
	@Autowired
	Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(DaznWalletGateway.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		CacheFactory cacheFactory = new CacheFactory();
		ICache cache = cacheFactory.getCache(env.getProperty(DaznConstants.CACHE_TYPE));
		
		cache.init(env);
		DaznConfigParams.init(env);
		
	}
	 		 
}