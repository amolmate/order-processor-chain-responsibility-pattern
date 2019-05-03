package com.morrisons.wholesale.dsd.endpoint.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.Categories;
import com.morrisons.wholesale.dsd.dto.DeliveryOpportunity;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.StoreCategory;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.WMMException;

public class WholesaleStoreServiceCaller {

	private IBaseGetEndPoint<ResponseEntity<Categories>> wholesaleStoreServiceEndPoint;

	private ExternalServiceConfig storeServiceConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleStoreServiceCaller.class);

	private static final long INTERVAL = 10;
	
	private AtomicInteger retryCounter = new AtomicInteger();

	@Autowired
	public WholesaleStoreServiceCaller(IBaseGetEndPoint<ResponseEntity<Categories>> wholesaleStoreServiceEndPoint,
			ExternalServiceConfig storeServiceConfig) {

		this.wholesaleStoreServiceEndPoint = wholesaleStoreServiceEndPoint;
		this.storeServiceConfig = storeServiceConfig;
	}

	public String call(Item data) {

		try {

			ParameterMappings mappings = getParameterMappings(data);

			ResponseEntity<Categories> response = wholesaleStoreServiceEndPoint.get(mappings);

			if (response.getStatusCodeValue() == 200) {

				Categories categories = response.getBody();

				List<StoreCategory> storeCategory = categories.getStoreCategories();

				List<DeliveryOpportunity> deliveryOpportunities = storeCategory.get(0).getDeliveryOpportunities();

				return deliveryOpportunities.get(0).getTransitInformation().getVirtualSellingLocation();
			} else {

				if (response.getStatusCodeValue() == 400) {

					return null;
				} else if (response.getStatusCodeValue() == 500) {
					
					retryCounter.set(0);
					retryStoreServiceCall(mappings);
				}
			}

		} catch (WMMException e) {

			LOGGER.error("Trace : ", e);
			LOGGER.debug("could not fetch DSD Orders. error code : {} ", e.getHttpStatusCode());
		}
		return null;
	}

	public void retryStoreServiceCall(ParameterMappings mappings) {

		Map<Object, ScheduledFuture<?>> scheduledTasksMap = new ConcurrentHashMap<>();

		TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

		Duration duration = Duration.ofSeconds(INTERVAL);

		Runnable task = new PollToStoreService(mappings, scheduledTasksMap);

		ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task;

		Instant startTime = Instant.now().plusMillis(500);

		ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(task, startTime, duration);

		scheduledTasksMap.put(runnable.getTarget(), future);
	}

	private ParameterMappings getParameterMappings(Item data) {

		ParameterMappings parameterMappings = new ParameterMappings();

		List<ParameterMapping> pathParameters = new ArrayList<>();
		List<ParameterMapping> queryParameters = new ArrayList<>();
		List<ParameterMapping> headerParameters = new ArrayList<>();

		pathParameters.add(new ParameterMapping("customerId", data.getCustomerId()));
		pathParameters.add(new ParameterMapping("storeId", data.getShipToLocationId()));

		queryParameters.add(new ParameterMapping("apikey", storeServiceConfig.getApiKey()));
		headerParameters.add(new ParameterMapping("Authorization", storeServiceConfig.getAuthorization()));

		parameterMappings.setPathParameters(pathParameters);
		parameterMappings.setQueryParameters(queryParameters);
		parameterMappings.setHeaderParameters(headerParameters);
		return parameterMappings;
	}

	private class PollToStoreService implements Runnable {

		private ParameterMappings mappings;

		private Map<Object, ScheduledFuture<?>> scheduledTasksMap;

		public PollToStoreService(ParameterMappings mappings, Map<Object, ScheduledFuture<?>> scheduledTasksMap) {

			this.mappings = mappings;
			this.scheduledTasksMap = scheduledTasksMap;
		}

		@Override
		public void run() {

			ResponseEntity<Categories> response = wholesaleStoreServiceEndPoint.get(mappings);

			if (response.getStatusCodeValue() == 400 || retryCounter.get() > 4) {

				cancelAllTasks();
			} else {
				
				retryCounter.incrementAndGet();
			}
		}

		private void cancelAllTasks() {

			scheduledTasksMap.forEach((k, v) -> {
				
				if (k instanceof ScheduledFuture<?>) {
					v.cancel(false);
				}
			});
		}
	}
}