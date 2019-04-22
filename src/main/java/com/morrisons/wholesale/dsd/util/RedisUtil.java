package com.morrisons.wholesale.dsd.util;

import java.util.HashMap;
import java.util.Map;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class RedisUtil
{
	public Map<String, Map<String, String>> getCatalogueItemMap(RedissonClient redissonClient, String catalogueKey)
	{
		log.debug("catalogueKey : {}", catalogueKey);

		Map<String, Map<String, String>> catalogueItemMap;

		RMap<String, Map<String, Map<String, String>>> map = redissonClient.getMap(catalogueKey);

		if (map.containsKey(catalogueKey))
		{
			log.debug("catalogueKeyfound");
			catalogueItemMap = map.get(catalogueKey);
		}
		else
		{
			log.debug("catalogueKeyNotfound");
			catalogueItemMap = new HashMap<>();
		}

		return catalogueItemMap;

	}

}
