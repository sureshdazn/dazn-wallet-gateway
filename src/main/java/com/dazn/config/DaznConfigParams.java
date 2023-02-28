package com.dazn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class DaznConfigParams {
	
	public static String jwkProviderUrl;
	
	public static void init(Environment env) {
		
		try {
			jwkProviderUrl = env.getProperty("jwk.provider.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
