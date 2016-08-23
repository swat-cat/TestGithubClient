package com.mermakov.testgithubclient.services.cache;

import android.support.annotation.NonNull;

import com.mermakov.testgithubclient.services.rest.dto.Identifiable;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class WeakMapHolder<K, V> implements MapHolder<K, V> {
    private Map<Object, WeakReference<V>> map;

    @NonNull
    @Override
    public V get(K key) {
        Object mapKey = getMapKey(key);

        if (map == null)
            map = new HashMap<>();

        WeakReference<V> valueWeak = map.get(mapKey);
        V value = valueWeak == null ? null : valueWeak.get();

        if (value == null) {
            value = create(key);
            map.put(mapKey, new WeakReference<>(value));
        }

        return value;
    }

    private Object getMapKey(K key) {
        if (key instanceof Identifiable) {
            return ((Identifiable) key).getIdentifier();
        } else {
            return key;
        }
    }

    @Override
    public V getDontCreate(K key) {
        if (map != null) {
            WeakReference<V> valueWeak = map.get(getMapKey(key));
            return valueWeak == null ? null : valueWeak.get();
        }

        return null;
    }

    @NonNull
    @Override
    public Collection<V> getAllDontCreate() {
        List<V> aliveValues = new LinkedList<>();
        if (map != null) {
            for (WeakReference<V> valueWeak : map.values()) {
                V value = valueWeak.get();
                if (value != null)
                    aliveValues.add(value);
            }
        }
        return aliveValues;
    }

    protected abstract V create(K key);
}
