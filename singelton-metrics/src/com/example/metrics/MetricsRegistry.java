package com.example.metrics;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Global metrics registry implemented as a lazy, thread-safe singleton.
 */
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Long> counters = new HashMap<>();

    // Guard against reflection creating a second instance.
    private static final AtomicBoolean CONSTRUCTED = new AtomicBoolean(false);

    private MetricsRegistry() {
        if (!CONSTRUCTED.compareAndSet(false, true)) {
            throw new IllegalStateException("MetricsRegistry singleton already created");
        }
    }

    // Holder idiom provides lazy initialization with thread safety.
    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
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
    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }
}