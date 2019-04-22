package com.morrisons.wholesale.dsd.validator;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;

public class ItemCategoryValidator extends BaseValidator<Item> {

	public ItemCategoryValidator(Customers customers) {

		super(customers);
	}

	@Override
	public boolean validate(Item data) {
		
		String itemCategoryFromConfig = getItemCategoryFromConfig(
				getSupplierWithName(getCustomerWithName(data.getCustomerName()), data.getSupplierName()));

		if (itemCategoryFromConfig == null) {

			return false;
		} else {

			if (StringUtils.isNotBlank("itemCategory")) {

				return "itemCategory".equals(itemCategoryFromConfig);
			} else {

				return false;
			}
		}
	}
	
	private String getItemCategoryFromConfig(SupportedSupplier supportedSupplier) {

		if (supportedSupplier == null) {

			return null;
		}
		//change
		return supportedSupplier.getName();
	}
}