package com.dazn.interceptors.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dazn.interceptors.wallet.JwtInterceptor;
import com.dazn.interceptors.wallet.RateLimiterInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	JwtInterceptor jwtInterceptor;
	
	@Autowired
	RateLimiterInterceptor rateLimiterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(rateLimiterInterceptor).addPathPatterns("/dazn/initiateWallet").order(1);
    	registry.addInterceptor(jwtInterceptor).addPathPatterns("/dazn/initiateWallet").order(2);
    	
    }

}
