package com.morrisons.wholesale.dsd.aggregationservice;

@FunctionalInterface
public interface AggregationService {
	
	void aggregate(String customerId);
}
