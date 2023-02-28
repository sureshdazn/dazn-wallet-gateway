package com.dazn.interceptors.validation;

import java.security.interfaces.RSAPublicKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dazn.config.DaznConfigParams;

public class JwtValidation {
	
    private static final Logger logger = LogManager.getLogger(JwtValidation.class);
	
    static Algorithm algorithm = null;

	public static boolean validateJwt(String jwtToken) {
		
		boolean isValidJwtToken = false;
		
		try {
			
			if(algorithm == null) {
				
				logger.info("JwtValidation : getting jwks algorithm");
				
				DecodedJWT jwt = JWT.decode(jwtToken); 
				JwkProvider provider = new UrlJwkProvider(DaznConfigParams.jwkProviderUrl); 
				Jwk jwk = provider.get(jwt.getKeyId()); 
				
				algorithm = Algorithm.RSA512((RSAPublicKey) jwk.getPublicKey(), null);
				
			}
			
			DecodedJWT jwt = JWT.decode(jwtToken); 
			
			algorithm.verify(jwt);
			
			isValidJwtToken = true; 
			  
		} catch(Exception e) {
			logger.error("JwtValidation : validateJwt : E : " , e);
		}
		
		logger.info("JwtValidation : validateJwt : isValidJwtToken : " + isValidJwtToken);
		
		return isValidJwtToken;		
		
	}

}
