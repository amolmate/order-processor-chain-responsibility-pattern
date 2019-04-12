package com.morrisons.wholesale.dsd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

	private String orderId;
	
	private String customerId;
	
	private String statusCurrent;
	
	private String messageType;

	private String orderRaisedDate;

	private String orderEffectiveDate;
}