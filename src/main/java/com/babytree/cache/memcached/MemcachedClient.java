package com.babytree.cache.memcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemcachedClient {
	private static MemcachedClient instense;
	private Memcached memObj;
	
	private MemcachedClient() {
		try {
			this.memObj = new Memcached();
			this.memObj.addServer("172.16.28.181", 11211, 1);
			this.memObj.addServer("172.16.28.181", 11212, 2);
			this.memObj.addServer("172.16.28.181", 11213, 3);
		} catch (Exception e) {
			
		}
	}

	public static MemcachedClient getInstense() {
		if (instense == null) {
			instense = new MemcachedClient();
		}
		return instense;
	}
	
	public String get(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return this.memObj.get(key);
    }
	

    public boolean set(String key, String value, int expire) {
    	return this.memObj.set(key, value, expire);
    }

    public boolean set(String key, String value) {
        return set(key, value, 0);
    }

    public boolean add(String key, String value, int expire) {
    	return this.memObj.add(key, value, expire);
    }

    public boolean add(String key, String value) {
        return add(key, value, 0);
    }

    public boolean replace(String key, String value, int expire) {
    	return this.memObj.replace(key, value, expire);
    }

    public boolean replace(String key, String value) {
        return replace(key, value, 0);
    }

    public void quit() throws IOException {
        this.memObj.quit();
    }
}
