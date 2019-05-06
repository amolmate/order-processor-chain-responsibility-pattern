package com.morrisons.wholesale.dsd.endpoint.impl;

import java.util.Map;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.RedisConfig;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;
import com.morrisons.wholesale.dsd.util.RedisUtil;
import com.morrisons.wholesale.dsd.util.ServiceUtil;
import com.morrisons.wholesale.dsd.util.Util;

@Component("redisCacheEndPoint")
public class RedisCacheCatlogueEndPoint  implements IRedisCacheEndPoint<String, RedisCatlogueItem> {

	private RedissonClient redissonClient;

	private RedisConfig redisConfig;

	@Autowired
	public RedisCacheCatlogueEndPoint(RedissonClient redissonClient, RedisConfig redisConfig) {

		this.redissonClient = redissonClient;
		this.redisConfig = redisConfig;
	}

	@Override
	public RedisCatlogueItem get(String key) {

		Map<String, Map<String, String>> map = RedisUtil.getCatalogueItemMap(redissonClient,
				ServiceUtil.getKeyForCatalogueItem(redisConfig.getEvn(), key));

		return Util.convertMapToDTO(map);
	}
}