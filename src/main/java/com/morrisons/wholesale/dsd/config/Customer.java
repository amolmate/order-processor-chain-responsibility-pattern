package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@Configuration
public class Customer {

	private String name;

	private String type;

	private String indexkey;

	private String indexvalue;
}