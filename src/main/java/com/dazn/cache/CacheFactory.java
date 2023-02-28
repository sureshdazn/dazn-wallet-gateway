package com.dazn.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.dazn.cache.data.CacheTypes;

public class CacheFactory {
	
    ICache cache = null;
    ConcurrentHashMap<CacheTypes, ICache> cacheObjects = new ConcurrentHashMap<>();

    public ICache getCache(String cacheType) {
    	
    	synchronized (this) {
    		
    		CacheTypes cacheTypeEnum = CacheTypes.valueOf(cacheType);
    		cache = cacheObjects.get(cacheTypeEnum);
    		
    		if(cache==null) {
    			    			
    			if (cacheTypeEnum.compareTo(CacheTypes.MEMCACHE) == 0) {
                    cache = new MemcachedElasticCache();
    			}
    			
    		}
    		
    		cacheObjects.put(cacheTypeEnum, cache);
    		
		}    	
        
        return cache;

    }

}
