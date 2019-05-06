package com.morrisons.wholesale.dsd.endpoint;

@FunctionalInterface
public interface IRedisCacheEndPoint<K, V> {

	V get(K key);
}
