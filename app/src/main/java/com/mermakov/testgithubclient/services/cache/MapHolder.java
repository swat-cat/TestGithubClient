package com.mermakov.testgithubclient.services.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

public interface MapHolder<K, V> {
    @NonNull
    V get(K key);

    @Nullable
    V getDontCreate(K key);

    @NonNull
    Collection<V> getAllDontCreate();
}
