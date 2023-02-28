package com.dazn.interceptors.wallet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dazn.data.DaznConstants;
import com.dazn.interceptors.validation.JwtValidation;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
    private static final Logger logger = LogManager.getLogger(JwtInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	                         Object handler) throws Exception {
		
		logger.info("JwtInterceptor : preHandle : api : " + request.getRequestURI());
		
		boolean preHandleSuccessful = false;
		
		String jwtAuthToken = request.getHeader(DaznConstants.HEADER_AUTHORIZATION);
		
		if((jwtAuthToken!=null)) {
			
			jwtAuthToken = jwtAuthToken.replace(DaznConstants.AUTHORIZATION_BEARER, "");
			jwtAuthToken = jwtAuthToken.strip();
			
			boolean isValidJwtToken = JwtValidation.validateJwt(jwtAuthToken);
			
			if(isValidJwtToken) {
				preHandleSuccessful = true;
			}
			
		}
		
		if(!preHandleSuccessful) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		
		logger.info("JwtInterceptor : preHandle : preHandleSuccessful : " + preHandleSuccessful);
				
		return preHandleSuccessful;
	
	}
	

}
