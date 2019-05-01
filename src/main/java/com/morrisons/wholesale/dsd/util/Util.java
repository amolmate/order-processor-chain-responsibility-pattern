package com.morrisons.wholesale.dsd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.morrisons.wholesale.dsd.configservice.OrderServiceConfigDescriptor;
import com.morrisons.wholesale.dsd.dto.Configuration;
import com.morrisons.wholesale.dsd.dto.Customer;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Util {

	@SuppressWarnings("unchecked")
	public static Customers convertMapToDTO(OrderServiceConfigDescriptor descriptormap) {

		if (descriptormap != null) {

			Customers customers = new Customers();

			List<Customer> list = new ArrayList<>();

			List<Map<String, Object>> mapList = (List<Map<String, Object>>) descriptormap.get("customers");

			for (Map<String, Object> map : mapList) {

				Customer customer = new Customer();

				customer.setName((String) map.get("name"));

				List<SupportedSupplier> supplierList = new ArrayList<>();

				List<Map<String, Object>> supplierMapList = (List<Map<String, Object>>) map.get("supportedSuppliers");

				for (Map<String, Object> supplierMap : supplierMapList) {

					SupportedSupplier supplier = new SupportedSupplier();

					//Map<String, String> configMap = (Map<String, String>) supplierMap.get("configuration");

					Configuration configuration = new Configuration();

					List<Object> listStores = (List<Object>) supplierMap.get("unsupportedStores");

					supplier.setId((String) supplierMap.get("id"));

					supplier.setConfiguration(configuration);

					supplier.setIdentifier((String) supplierMap.get("identifier"));

					supplier.setItemBaseType((String) supplierMap.get("itemBaseType"));

					supplier.setName((String) supplierMap.get("name"));

					supplier.setUnsupportedStores(listStores);

					supplier.setUom("uom");

					supplierList.add(supplier);
				}

				customer.setSupportedSuppliers(supplierList);

				list.add(customer);
			}

			customers.setCustomers(list);

			return customers;
		}

		return null;
	}
}