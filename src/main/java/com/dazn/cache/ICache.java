package com.dazn.cache;

import org.springframework.core.env.Environment;

import com.dazn.exception.DaznException;

public interface ICache {
	
	void init(Environment env);
	
	Object getCache(String key) throws DaznException;

	boolean upsertCacheEntryAsync(String key, Object value, int expiryInSecs, int asyncTimeOutInSecs) throws DaznException;
	boolean deleteCacheAsync(String key, int asyncTimeOutInSecs) throws DaznException;
	boolean flushCacheAsync(int asyncTimeOutInSecs) throws DaznException;
	
	boolean upsertCacheEntry(String key, Object value, int expiryInSecs) throws DaznException;
	boolean deleteCache(String key) throws DaznException;
	boolean flushCache() throws DaznException;
	
}
