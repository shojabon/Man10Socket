package com.shojabon.man10socket.utils;

import java.util.concurrent.*;
import java.util.Map.Entry;

public class ConcurrentHashMapWithTimeout<K, V> {

    private final ConcurrentHashMap<K, V> map;
    private final ConcurrentHashMap<K, Long> timeMap;
    private final int ttl; // time-to-live in seconds
    private final ScheduledExecutorService executorService;

    public ConcurrentHashMapWithTimeout(int ttl) {
        this.map = new ConcurrentHashMap<>();
        this.timeMap = new ConcurrentHashMap<>();
        this.ttl = ttl;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        startCleanupTask();
    }

    private void startCleanupTask() {
        executorService.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            for (Entry<K, Long> entry : timeMap.entrySet()) {
                if (currentTime - entry.getValue() > ttl * 1000) {
                    map.remove(entry.getKey());
                    timeMap.remove(entry.getKey());
                }
            }
        }, ttl, ttl, TimeUnit.SECONDS);
    }

    public V put(K key, V value) {
        timeMap.put(key, System.currentTimeMillis());
        return map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public V remove(K key) {
        timeMap.remove(key);
        return map.remove(key);
    }

    public boolean containsKey(K key){
        return map.containsKey(key);
    }

    // Other methods like clear, size, etc, can be added as needed

    // Shutdown method to properly close the scheduled executor service
    public void shutdown() {
        executorService.shutdown();
    }
}
