package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;

public abstract class BaseValidator<T> {

	protected Map<String, Map<String, SupportedSupplier>> customers;

	private IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint;

	public BaseValidator(Map<String, Map<String, SupportedSupplier>> customers) {

		this.customers = customers;
	}

	public BaseValidator(IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint) {

		this.redisCacheEndPoint = redisCacheEndPoint;
	}

	protected SupportedSupplier getSupplier(String customerName, String supplierName) {

		Map<String, SupportedSupplier> supplierMap = customers.get(customerName);

		if (supplierMap == null) {

			return null;
		} else {

			return supplierMap.get(supplierName);
		}
	}

	protected Map<String, Object> getItemFromRedisCatlogue(String key) {

		// redis code to fetch item from redis
		redisCacheEndPoint.get(key);
		return null;
	}

	public abstract boolean validate(T data);
}