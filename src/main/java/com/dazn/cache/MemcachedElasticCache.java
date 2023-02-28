package com.dazn.cache;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.dazn.data.DaznConstants;
import com.dazn.exception.DaznException;

import net.spy.memcached.MemcachedClient;

@Configuration
public class MemcachedElasticCache implements ICache {
	
	private MemcachedClient memcachedClient = null;
	
	@Value("${memcached.cluster.endpoint.hostname}")
	private String memcachedHost;
	
	@Value("${memcached.cluster.endpoint.port}")
	private int memcachedPort;
	
	@Value("${memcached.client.shutdown.timeout.in.secs}")
	private int clientShutdownTimeoutInSecs;
	
	@Override
	public void init(Environment env) {
		
		try {
			
			memcachedHost = env.getProperty("memcached.cluster.endpoint.hostname");
			memcachedPort = Integer.parseInt(env.getProperty("memcached.cluster.endpoint.port"));
			clientShutdownTimeoutInSecs = Integer.parseInt(env.getProperty("memcached.client.shutdown.timeout.in.secs"));
			
			memcachedClient = new MemcachedClient(new InetSocketAddress(memcachedHost, memcachedPort));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void stopClient() {
		 memcachedClient.shutdown(clientShutdownTimeoutInSecs, TimeUnit.SECONDS);
	}

	@Override
	public boolean upsertCacheEntryAsync(String key, Object value, int expiryInSecs, int asyncTimeOutInSecs) throws DaznException  {
		
		boolean isUpsertCacheSuccess = false;
		boolean valueExists = (getCacheValue(key) != null);
		
		try {
			
			if(valueExists) {
				//log here
			}
			
			Future<Boolean> result = memcachedClient.set(key, expiryInSecs, value);
			isUpsertCacheSuccess = result.get(asyncTimeOutInSecs,TimeUnit.SECONDS).booleanValue();
		
		} catch (Exception e) {
            throw new DaznException(DaznConstants.MEM_CACHE_EXCEPTION, "", e);
		}
		
		return isUpsertCacheSuccess;
		
	}

	@Override
	public Object getCache(String key) throws DaznException {
		return getCacheValue(key);
	}

	@Override
	public boolean deleteCacheAsync(String key, int asyncTimeOutInSecs) throws DaznException {
		
		boolean isDeleteCacheSuccess = false;
		
		try {
			
			Future<Boolean> result = memcachedClient.delete(key);
			isDeleteCacheSuccess = result.get(asyncTimeOutInSecs, TimeUnit.SECONDS).booleanValue();
			
		} catch (Exception e) {
            throw new DaznException(DaznConstants.MEM_CACHE_EXCEPTION, "", e);
		}
		
		return isDeleteCacheSuccess;
		
	}

	@Override
	public boolean flushCacheAsync(int asyncTimeOutInSecs) throws DaznException {
		
		boolean isCacheFlushSuccess = false;
		
		try {
			
			Future<Boolean> result = memcachedClient.flush();
			isCacheFlushSuccess = result.get(asyncTimeOutInSecs, TimeUnit.SECONDS).booleanValue();
			
		} catch (Exception e) {
            throw new DaznException(DaznConstants.MEM_CACHE_EXCEPTION, "", e);
		}
		
		return isCacheFlushSuccess;
		
	}
	
	private Object getCacheValue(String key) throws DaznException {
		
		Object cacheObj = null;
		
		try {
			cacheObj = memcachedClient.get(key);
		} catch(Exception e) {
            throw new DaznException(DaznConstants.MEM_CACHE_EXCEPTION, "", e);
		}
		
		return cacheObj;
	
	}

	@Override
	public boolean upsertCacheEntry(String key, Object value, int expiryInSecs) throws DaznException {
		return false;
	}

	@Override
	public boolean deleteCache(String key) throws DaznException {
		return false;
	}

	@Override
	public boolean flushCache() throws DaznException {
		return false;
	}
	
}
