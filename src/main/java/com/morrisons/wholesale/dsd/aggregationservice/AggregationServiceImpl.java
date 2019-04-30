package com.morrisons.wholesale.dsd.aggregationservice;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

import com.morrisons.wholesale.dsd.dto.AggregationPayload;
import com.morrisons.wholesale.dsd.dto.PollingResponse;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBasePostEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Service
public class AggregationServiceImpl implements AggregationService {

	private IBasePostEndPoint<AggregationPayload, String> aggregationEndPoint;

	private IBaseGetEndPoint<PollingResponse> pollingEndPoint;

	private static final long INTERVAL = 120000;

	@Autowired
	public AggregationServiceImpl(IBasePostEndPoint<AggregationPayload, String> aggregationEndPoint,
			IBaseGetEndPoint<PollingResponse> pollingEndPoint) {

		this.aggregationEndPoint = aggregationEndPoint;
		this.pollingEndPoint = pollingEndPoint;
	}

	@Override
	public void aggregate(String customerId) {

		String response = aggregationEndPoint.post(getParameterMappings(customerId), getAggregationPayload());
		// get jobId from response
		String jobId = response;
		startPolling(customerId, jobId);
	}

	private void startPolling(String customerId, String jobId) {

		long pollingTime = System.currentTimeMillis();

		Map<Object, ScheduledFuture<?>> scheduledTasksMap = new ConcurrentHashMap<>();

		TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

		Duration duration = Duration.ofSeconds(INTERVAL);

		Runnable task = new PollingTask(customerId, jobId, scheduledTasksMap, pollingTime);

		ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task;

		Instant startTime = Instant.now().plusMillis(500);

		ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(task, startTime, duration);

		scheduledTasksMap.put(runnable.getTarget(), future);
	}

	private ParameterMappings getParameterMappings(String customerId) {

		ParameterMappings mappings = new ParameterMappings();
		List<ParameterMapping> pathParameters = new ArrayList<>();
		pathParameters.add(new ParameterMapping("customerId", customerId));
		mappings.setPathParameters(pathParameters);
		return mappings;
	}

	private AggregationPayload getAggregationPayload() {

		AggregationPayload aggregationPayload = new AggregationPayload();

		aggregationPayload.setEndTime("endTime");
		aggregationPayload.setId("id");
		aggregationPayload.setName("name");
		aggregationPayload.setStartTime("startTime");

		return aggregationPayload;
	}

	private class PollingTask implements Runnable {

		private String customerId;

		private String jobId;

		private Map<Object, ScheduledFuture<?>> scheduledTasksMap;

		private long pollingTime;

		public PollingTask(String customerId, String jobId, Map<Object, ScheduledFuture<?>> scheduledTasksMap,
				long pollingTime) {

			this.customerId = customerId;
			this.jobId = jobId;
			this.scheduledTasksMap = scheduledTasksMap;
			this.pollingTime = pollingTime;
		}

		@Override
		public void run() {

			PollingResponse response = pollingEndPoint.get(getParameterMappings(customerId, jobId));

			String status = response.getStatus();

			if (StringUtils.isNotBlank(status) && status.equalsIgnoreCase("COMPLETE")) {

				// stop all tasks
				cancelAllTasks();
			} else if (StringUtils.isNotBlank(status) && !status.equalsIgnoreCase("COMPLETE") && isTimedOut()) {
				// print logs accordingly
			}
		}

		private void cancelAllTasks() {

			scheduledTasksMap.forEach((k, v) -> {
				if (k instanceof ScheduledFuture<?>) {
					v.cancel(false);
				}
			});
		}

		private boolean isTimedOut() {

			return System.currentTimeMillis() - pollingTime > 1000 * 60 * 60;
		}

		private ParameterMappings getParameterMappings(String customerId, String jobId) {

			ParameterMappings mappings = new ParameterMappings();
			List<ParameterMapping> pathParameters = new ArrayList<>();
			pathParameters.add(new ParameterMapping("customerId", customerId));
			pathParameters.add(new ParameterMapping("jobId", jobId));
			mappings.setPathParameters(pathParameters);
			return mappings;
		}
	}
}