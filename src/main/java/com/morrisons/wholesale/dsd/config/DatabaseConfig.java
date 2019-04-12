package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author amol13704
 *
 */

@Getter
@Setter
@ToString
@Configuration
public class DatabaseConfig {

	private String username;

	private String password;

	private String url;
}
