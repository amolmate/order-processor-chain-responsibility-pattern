package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.endpoint.impl.WholesaleStoreServiceCaller;

public class ItemShipToLocationIdValidator extends BaseValidator<Item> {

	private WholesaleStoreServiceCaller wholesaleStoreServiceCaller;

	public ItemShipToLocationIdValidator(Map<String, Map<String, SupportedSupplier>> customers,
			WholesaleStoreServiceCaller wholesaleStoreServiceCaller) {

		super(customers);
		this.wholesaleStoreServiceCaller = wholesaleStoreServiceCaller;
	}

	@Override
	public boolean validate(Item data) {

		String saleId = wholesaleStoreServiceCaller.call(data);

		if (StringUtils.isNotBlank(saleId)) {

			// saleId exctrated do further action
			return true;
		} else {

			return false;
		}
	}
}