package com.babytree.cache.memcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;

class Memcached {

    private MemcachedClient client;
    protected PHPTranscoder transcoder;

    public Memcached() throws IOException {
        transcoder = new PHP7Transcoder();
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder();
        builder.setSessionLocator(new KetamaMemcachedSessionLocator(true));
        client = builder.build();
    }

    public void setTranscoder(PHPTranscoder transcoder) {
        this.transcoder = transcoder;
    }

    public void addServer(String server, int port, int weight) {
        try {
            client.addServer(server, port, weight);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addServer(String server, int port) {
        this.addServer(server, port, 1);
    }

    public String get(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return client.get(key, transcoder);
    }

    public boolean set(String key, String value, int expire) {
        boolean b = false;
        try {
            b = client.set(key, expire, value, transcoder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public boolean set(String key, String value) {
        return set(key, value, 0);
    }

    public boolean add(String key, String value, int expire) {
        boolean b = false;
        try {
            b = client.add(key, expire, value, transcoder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public boolean add(String key, String value) {
        return add(key, value, 0);
    }

    public boolean replace(String key, String value, int expire) {
        boolean b = false;
        try {
            b = client.add(key, expire, value, transcoder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public boolean replace(String key, String value) {
        return replace(key, value, 0);
    }

    public void quit() throws IOException {
        client.shutdown();
    }
}