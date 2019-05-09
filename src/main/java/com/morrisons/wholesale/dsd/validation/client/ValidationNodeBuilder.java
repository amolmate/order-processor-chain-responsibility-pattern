package com.morrisons.wholesale.dsd.validation.client;

import java.util.Map;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.caller.WholesaleStoreServiceCaller;
import com.morrisons.wholesale.dsd.validation.node.INode;
import com.morrisons.wholesale.dsd.validation.node.impl.ItemBaseTypeValidatonNode;
import com.morrisons.wholesale.dsd.validation.node.impl.ItemCaseSizeValidatonNode;
import com.morrisons.wholesale.dsd.validation.node.impl.ItemCategoryValidatonNode;
import com.morrisons.wholesale.dsd.validation.node.impl.ItemIdValidatonNode;
import com.morrisons.wholesale.dsd.validation.node.impl.ItemShipToLocationIdValidatonNode;
import com.morrisons.wholesale.dsd.validation.node.impl.ItemUomValidatonNode;
import com.morrisons.wholesale.dsd.validation.validator.ItemBaseTypeValidator;
import com.morrisons.wholesale.dsd.validation.validator.ItemCaseSizeValidator;
import com.morrisons.wholesale.dsd.validation.validator.ItemCategoryValidator;
import com.morrisons.wholesale.dsd.validation.validator.ItemIdValidator;
import com.morrisons.wholesale.dsd.validation.validator.ItemShipToLocationIdValidator;
import com.morrisons.wholesale.dsd.validation.validator.ItemUomValidator;

public class ValidationNodeBuilder {

	private static INode<Item> validationNode;

	private ValidationNodeBuilder() {

	}

	public static INode<Item> build(Map<String, Map<String, SupportedSupplier>> customers,
			IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint,
			WholesaleStoreServiceCaller wholesaleStoreServiceCaller) {

		if (validationNode != null) {

			return validationNode;
		} else {

			validationNode = new ItemBaseTypeValidatonNode(new ItemBaseTypeValidator(customers),
					new ItemUomValidatonNode(new ItemUomValidator(customers), new ItemCaseSizeValidatonNode(
							new ItemCaseSizeValidator(redisCacheEndPoint),
							new ItemCategoryValidatonNode(new ItemCategoryValidator(customers), new ItemIdValidatonNode(
									new ItemIdValidator(redisCacheEndPoint),
									new ItemShipToLocationIdValidatonNode(
											new ItemShipToLocationIdValidator(customers, wholesaleStoreServiceCaller),
											null))))));
			return validationNode;
		}
	}
}