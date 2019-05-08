package com.morrisons.wholesale.dsd.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is for loading exception details from configuration file -
 * EXCEPTIONS_FILE. A public method is provided which returns exception details
 * for an error code defined in the configuration file.
 * 
 * @author amol13704
 *
 */
@Component
public class ExceptionConfigLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionConfigLoader.class);

	private static final String EXCEPTIONS_FILE = "/WMMExceptions.json";

	private Map<Integer, ExceptionConfigDetails> mapExceptions = Collections.emptyMap();

	public ExceptionConfigLoader() {
		initializeExceptionMap();
	}

	/**
	 * Cache in the map
	 */
	public void initializeExceptionMap() {

		ExceptionConfigListWrapper wrapper = loadExceptions();
		if (wrapper != null) {

			List<ExceptionConfigDetails> messages = wrapper.getExceptions();
			if (messages != null && !messages.isEmpty()) {

				mapExceptions = new HashMap<>();
				for (ExceptionConfigDetails message : messages) {

					int errorCode = message.getErrorCode();
					LOGGER.debug("errorCode : {}", errorCode);

					mapExceptions.put(errorCode, message);
				}
				LOGGER.debug("Loaded and Cached {} exceptions.", mapExceptions.size());
			}
		}
	}

	/**
	 * Load the exception definitions from the configuration file
	 * 
	 * @return
	 */
	private ExceptionConfigListWrapper loadExceptions() {

		try {
			ExceptionConfigListWrapper wrapper = null;
			InputStream configStream = this.getClass().getResourceAsStream(getExceptionsFile());
			if (configStream != null) {
				wrapper = new ObjectMapper().readValue(configStream, ExceptionConfigListWrapper.class);
			}
			return wrapper;
		} catch (IOException e) {

			String message = "Exception occured while reading file : " + getExceptionsFile();
			ConfigServiceException we = new ConfigServiceException(ErrorCodes.EXCEPTIONS_FILE_LOADING_ERR, message, e,
					ConfigServiceException.DEFAULT_HTTP_STATUS_CODE);
			LOGGER.error(message, we);
			throw we;
		}
	}

	/**
	 * This can be overridden in case the configuration file name to be used has
	 * a different name
	 * 
	 * @return
	 */
	protected String getExceptionsFile() {
		return EXCEPTIONS_FILE;
	}

	/**
	 * Provides exceptions details for an error code
	 * 
	 * @param errorCode
	 * @return
	 */
	public ExceptionConfigDetails getErrorMessage(int errorCode) {
		return mapExceptions.get(errorCode);
	}
}