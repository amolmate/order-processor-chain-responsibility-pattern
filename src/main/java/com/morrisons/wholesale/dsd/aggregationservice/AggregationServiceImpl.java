package com.morrisons.wholesale.dsd.aggregationservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.morrisons.wholesale.dsd.dto.AggregationPayload;
import com.morrisons.wholesale.dsd.endpoint.BasePostEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

public class AggregationServiceImpl implements AggregationService {

	private BasePostEndPoint<AggregationPayload, Response> basePostEndPoint;

	@Autowired
	public AggregationServiceImpl(BasePostEndPoint<AggregationPayload, Response> basePostEndPoint) {

		this.basePostEndPoint = basePostEndPoint;
	}

	@Override
	public void aggregate() {

		basePostEndPoint.post(getParameterMappings(), getAggregationPayload());
	}

	private ParameterMappings getParameterMappings() {

		ParameterMappings mappings = new ParameterMappings();
		List<ParameterMapping> pathParameters = new ArrayList<>();
		pathParameters.add(new ParameterMapping("customerId", ""));
		mappings.setPathParameters(pathParameters);
		return mappings;
	}

	private AggregationPayload getAggregationPayload() {

		AggregationPayload aggregationPayload = new AggregationPayload();
		/*aggregationPayload.setEndTime(endTime);
		aggregationPayload.setId(id);
		aggregationPayload.setName(name);
		aggregationPayload.setStartTime(startTime);*/
		return aggregationPayload;
	}
}
