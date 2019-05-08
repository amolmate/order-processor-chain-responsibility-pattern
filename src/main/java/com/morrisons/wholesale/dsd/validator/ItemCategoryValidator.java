package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;

public class ItemCategoryValidator extends BaseValidator<Item> {

	public ItemCategoryValidator(Map<String, Map<String, SupportedSupplier>> customers) {

		super(customers);
	}

	@Override
	public boolean validate(Item data) {

		String itemCategoryFromConfig = getItemCategoryFromConfig(
				getSupplier(data.getCustomerName(), data.getSupplierName()));

		if (itemCategoryFromConfig == null) {

			return false;
		} else {

			if (StringUtils.isNotBlank(itemCategoryFromConfig) && StringUtils.isNotBlank(data.getItemCategory())) {

				return itemCategoryFromConfig.equals(data.getItemCategory());
			} else {

				return false;
			}
		}
	}

	private String getItemCategoryFromConfig(SupportedSupplier supportedSupplier) {

		if (supportedSupplier == null) {

			return null;
		}
		
		return supportedSupplier.getItemCategory();
	}
}