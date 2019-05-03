package com.morrisons.wholesale.dsd.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.config.S3FileConfiguration;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.loader.ApplicationConfigLoader;
import com.morrisons.wholesale.dsd.service.file.S3FileDetail;

/**
 * 
 * @author surajv
 *
 */
public class DataBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataBuilder.class);

	public static ApplicationConfig getConfig(String configFilePath) {

		try (InputStream in = ApplicationConfigLoader.class.getClassLoader().getResourceAsStream(configFilePath)) {

			return new Yaml().loadAs(in, ApplicationConfig.class);

		} catch (Exception e) {
			String msg = "Exception occurred while initializing configuration from config path : " + configFilePath;
			WMMException ie = new WMMException(ErrorCodes.APP_CONF_LOAD_ERR, msg, e,WMMException.DEFAULT_HTTP_STATUS_CODE);
			LOGGER.error(msg, ie);
			throw ie;
		}
	}

	public static ParameterMappings getParameterMappings() {

		ParameterMapping pm = new ParameterMapping("X", "Y");

		List<ParameterMapping> lpm = new ArrayList<>();
		lpm.add(pm);

		ParameterMappings pms = new ParameterMappings();

		pms.setHeaderParameters(lpm);
		pms.setPathParameters(lpm);
		pms.setQueryParameters(lpm);

		return pms;
	}

	public static S3FileConfiguration getS3FileConfiguration() {

		S3FileConfiguration s3FileConfiguration = new S3FileConfiguration();

		s3FileConfiguration.setBucket("X");
		s3FileConfiguration.setFileExtn("Y");
		s3FileConfiguration.setFileName("Z");
		s3FileConfiguration.setFolder("A");

		return s3FileConfiguration;
	}

	public static S3FileDetail getS3FileDetail() {

		S3FileDetail detail = new S3FileDetail("X", "Y");

		return detail;
	}
}
