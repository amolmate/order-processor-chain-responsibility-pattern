package com.morrisons.wholesale.dsd.processor;

public abstract class WholesaleDSDOrderProcessorTemplate<D, O, R> implements IWholesaleDSDOrderProcessor {

	@Override
	public void processTask() {
		
		// Retrieve customer list and supplier list from config service 
		D data = getDataFromConifgService();
		
		// get dsd orders till date with status raised
		getDSDOrdersWithStatusRaised(data);
		
		// validate all orders and items
		
		
		
	}

	protected abstract D getDataFromConifgService();
	
	protected abstract O getDSDOrdersWithStatusRaised(D data);
	
	protected abstract R validateDSDOrders();
}