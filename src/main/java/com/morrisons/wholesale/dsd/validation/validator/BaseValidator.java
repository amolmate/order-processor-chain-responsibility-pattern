package com.morrisons.wholesale.dsd.validation.validator;

import java.util.Map;

import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

public abstract class BaseValidator<T> {

	protected Map<String, Map<String, SupportedSupplier>> customers;

	private IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint;

	public BaseValidator(Map<String, Map<String, SupportedSupplier>> customers) {

		this.customers = customers;
	}

	public BaseValidator(IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint) {

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

	protected RedisCatlogueItem getItemFromRedisCatlogue(ParameterMappings mappings) {

		// redis code to fetch item from redis
		return redisCacheEndPoint.get(mappings);
	}

	public abstract boolean validate(T data);
}