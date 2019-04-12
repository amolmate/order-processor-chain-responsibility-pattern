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
public class S3Configuration {

	private String bucketName;

	private String folderName;

	private String fileName;
	
	private String fileExtension;
}