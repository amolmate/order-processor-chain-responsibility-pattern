package com.morrisons.wholesale.dsd.util;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.morrisons.wholesale.dsd.constant.Constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceUtil {

	public String getKeyForCatalogueItem(String env, String customerIdMessageType) {

		return StringUtils.join(customerIdMessageType, Constants.HYPHEN + Constants.CATALOGUE_ITEMS_KEY,
				Constants.HYPHEN, env);
	}

	public String getCustomerMessage(String customerId, String messageType) {

		return StringUtils.join(StringUtils.join(customerId, "-", messageType));
	}

	public String getCustomerOrderId(String customerId, String orderId) {

		return StringUtils.join(StringUtils.join(customerId, orderId));
	}

	public List<String> getBlankFields(Item item) {

		return item.hasAttribute(Constants.NON_MANDATORY_FIELDS) ? item.getList(Constants.NON_MANDATORY_FIELDS)
				: Collections.emptyList();
	}
}