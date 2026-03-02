package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static volatile boolean instantiated = false;
    private final Map<String, Long> counters = new HashMap<>();

    private MetricsRegistry() {
        if (instantiated) {
            throw new IllegalStateException("MetricsRegistry is a singleton. Use getInstance().");
        }
        instantiated = true;
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    @Serial
    private Object readResolve() {
        return getInstance();
    }
}