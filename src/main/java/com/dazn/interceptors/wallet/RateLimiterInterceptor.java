package com.dazn.interceptors.wallet;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {
	
    private static final Logger logger = LogManager.getLogger(RateLimiterInterceptor.class);

	private ConcurrentHashMap<String, Bucket> rateLimiterBuckets = new ConcurrentHashMap<>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	                         Object handler) throws Exception {
		
		logger.info("RateLimiterInterceptor : preHandle : api : " + request.getRequestURI());
			
		boolean preHandleSuccessful = false;

		synchronized (this) {
						
			Bucket rateLimiterBucket = rateLimiterBuckets.get(request.getRequestURI());
			
			logger.info("RateLimiterInterceptor : preHandle : rateLimiterBucket : " + rateLimiterBucket);
			
			if(rateLimiterBucket == null) {
				
				Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(2)));
				rateLimiterBucket = Bucket4j.builder().addLimit(limit).build();
			
				rateLimiterBuckets.put(request.getRequestURI(), rateLimiterBucket);
				
			}
			
			if((rateLimiterBucket.tryConsume(1))) {
				preHandleSuccessful = true;
			}
			
		}
		
		logger.info("RateLimiterInterceptor : preHandle : preHandleSuccessful : " + preHandleSuccessful);
		
		if(!preHandleSuccessful) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		}
		
		return preHandleSuccessful;

	}

}
