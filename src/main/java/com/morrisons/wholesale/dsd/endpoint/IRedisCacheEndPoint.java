package com.morrisons.wholesale.dsd.endpoint;

public interface IRedisCacheEndPoint<K, V> {

	V get(K key);
}
