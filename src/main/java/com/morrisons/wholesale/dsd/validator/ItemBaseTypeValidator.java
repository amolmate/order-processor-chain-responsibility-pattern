package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;

public class ItemBaseTypeValidator extends BaseValidator<Item> {

	public ItemBaseTypeValidator(Map<String, Map<String, SupportedSupplier>> customers) {

		super(customers);
	}

	@Override
	public boolean validate(Item data) {

		String itemBaseTypeFromConfig = getItemBaseTypeFromConfig(
				getSupplier(data.getCustomerName(), data.getSupplierName()));

		if (itemBaseTypeFromConfig == null) {

			return false;
		} else {

			if (StringUtils.isNotBlank(data.getItemBaseType())) {

				return data.getItemBaseType().equals(itemBaseTypeFromConfig);
			} else {

				return false;
			}
		}
	}

	private String getItemBaseTypeFromConfig(SupportedSupplier supportedSupplier) {

		if (supportedSupplier == null) {

			return null;
		}

		return supportedSupplier.getName();
	}
}