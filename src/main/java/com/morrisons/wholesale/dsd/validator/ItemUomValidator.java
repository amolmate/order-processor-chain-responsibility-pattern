package com.morrisons.wholesale.dsd.validator;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;

public class ItemUomValidator extends BaseValidator<Item> {

	public ItemUomValidator(Customers customers) {

		super(customers);
	}

	@Override
	public boolean validate(Item data) {

		String itemUom = getItemUOMFromConfig(
				getSupplierWithName(getCustomerWithName(data.getCustomerName()), data.getSupplierName()));

		if (itemUom == null) {

			return false;
		} else {

			if (StringUtils.isNotBlank(data.getUom())) {

				return data.getUom().equals(itemUom);
			} else {

				return false;
			}
		}
	}

	private String getItemUOMFromConfig(SupportedSupplier supportedSupplier) {

		if (supportedSupplier == null) {

			return null;
		}

		return supportedSupplier.getUom();
	}
}