package com.morrisons.wholesale.dsd.endpoint.impl;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.RedisConfig;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;

@Component
public class RedisCacheCatlogueEndPoint implements IRedisCacheEndPoint<String, RedisCatlogueItem> {

	private RedissonClient redissonClient;

	private RedisConfig redisConfig;

	@Autowired
	public RedisCacheCatlogueEndPoint(RedissonClient redissonClient, RedisConfig config) {

		this.redissonClient = redissonClient;
		this.redisConfig = config;
	}

	@Override
	public RedisCatlogueItem get(String key) {

		return getRedisCatalogueItems(key);
	}

	private RedisCatlogueItem getRedisCatalogueItems(String key) {

		/*
		 * return RedisUtil.getCatalogueItemMap(redissonClient,
		 * ServiceUtil.getKeyForCatalogueItem(redisConfig.getEvn(), key));
		 */
		// for now
		return new RedisCatlogueItem();
	}
}