package com.morrisons.wholesale.dsd.endpoint.impl;

import java.util.Map;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.RedisConfig;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;
import com.morrisons.wholesale.dsd.util.RedisUtil;
import com.morrisons.wholesale.dsd.util.ServiceUtil;

@Component
public class RedisCacheCatlogueEndPoint implements IRedisCacheEndPoint<String, Map<String, Map<String, String>>> {

	private RedissonClient redissonClient;

	private RedisConfig redisConfig;

	@Autowired
	public RedisCacheCatlogueEndPoint(RedissonClient redissonClient, RedisConfig config) {

		this.redissonClient = redissonClient;
		this.redisConfig = config;
	}

	@Override
	public Map<String, Map<String, String>> get(String key) {

		return getRedisCatalogueItems(key);
	}

	private Map<String, Map<String, String>> getRedisCatalogueItems(String key) {

		return RedisUtil.getCatalogueItemMap(redissonClient,
				ServiceUtil.getKeyForCatalogueItem(redisConfig.getEvn(), key));
	}
}