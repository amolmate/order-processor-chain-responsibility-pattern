package com.morrisons.wholesale.dsd.validator;

import java.util.Optional;

import com.morrisons.wholesale.dsd.dto.Customer;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;

public abstract class BaseValidator<T> {

	protected Customers customers;

	public BaseValidator(Customers customers) {

		this.customers = customers;
	}

	protected Customer getCustomerWithName(String name) {

		Optional<Customer> customer = customers.getCustomers().stream().filter(c -> c.getName().equals(name))
				.findFirst();

		if (customer.isPresent()) {

			return customer.get();
		} else {
			return null;
		}
	}

	protected SupportedSupplier getSupplierWithName(Customer customer, String name) {

		if (customer == null) {

			return null;
		} else {

			Optional<SupportedSupplier> supportedSupplier = customer.getSupportedSuppliers().stream()
					.filter(c -> c.getName().equals(name)).findFirst();

			if (supportedSupplier.isPresent()) {

				return supportedSupplier.get();
			} else {

				return null;
			}
		}
	}
	
	public abstract boolean validate(T data);
}