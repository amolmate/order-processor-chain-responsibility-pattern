package com.morrisons.wholesale.dsd.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGDAO;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGErrorDAO;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGRetryDAO;
import com.morrisons.wholesale.dsd.endpoint.IBasePostEndPoint;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.model.external.stock.StockMovement;
import com.morrisons.wholesale.dsd.service.ITransformer;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;
import com.morrisons.wholesale.dsd.vo.ServiceCallStatus;
import com.morrisons.wholesale.dsd.vo.StockMovementSvcRespWrapper;

@RunWith(MockitoJUnitRunner.class)
public class StockDataServiceImplTest {

	@InjectMocks
	StockDataServiceImpl stockDataServiceImpl;

	@Mock
	private ApplicationConfig applicationConfig;

	@Mock
	private IStockMovementSTGDAO stockMovementSTGDAO;

	@Mock
	private IStockMovementSTGErrorDAO stockMovementSTGErrorDAO;

	@Mock
	private IStockMovementSTGRetryDAO stockMovementSTGRetryDAO;

	@Mock
	private ITransformer transformer;

	@Mock
	private IBasePostEndPoint<StockMovement, Response> stockMovementServiceEndPoint;

	@Mock
	private IWMMExceptionFactory exceptionFactory;

	@Before
	public void init() {
		Mockito.when(exceptionFactory.createException(Mockito.anyInt(), Mockito.anyString(), Mockito.any()))
				.thenReturn(Mockito.mock(WMMException.class));
	}

	@Test
	public void getUniqueSources_test() {
		stockDataServiceImpl.getUniqueSources(10l);
	}

	@Test
	public void processData_test() {
		ProcessDetails pd = new ProcessDetails();
		List<Object[]> value = new ArrayList<>();
		Object[] val = new Object[4];
		val[1] = "Abc";
		val[2] = "Abc";
		val[3] = new Date();

		value.add(val);

		Mockito.when(stockMovementSTGDAO.fetchDistinctGroup(Mockito.anyString(), Mockito.anyMap())).thenReturn(value);
		Mockito.when(applicationConfig.getThreadPoolSize()).thenReturn(1);
		stockDataServiceImpl.processData(pd, "sourceId");
	}

	@Test(expected = WMMException.class)
	public void processData_throw_runtime_exception_test() {
		ProcessDetails pd = new ProcessDetails();
		List<Object[]> value = new ArrayList<>();
		Mockito.when(stockMovementSTGDAO.fetchDistinctGroup(Mockito.anyString(), Mockito.anyMap()))
				.thenThrow(WMMException.class);
		Mockito.when(applicationConfig.getThreadPoolSize()).thenReturn(1);
		stockDataServiceImpl.processData(pd, "sourceId");
	}

	@Test(expected = WMMException.class)
	public void processData_throw_exception_test() {
		ProcessDetails pd = new ProcessDetails();
		List<Object[]> value = new ArrayList<>();
		Mockito.when(stockMovementSTGDAO.fetchDistinctGroup(Mockito.anyString(), Mockito.anyMap()))
				.thenThrow(Exception.class);
		Mockito.when(applicationConfig.getThreadPoolSize()).thenReturn(1);
		stockDataServiceImpl.processData(pd, "sourceId");
	}

	@Test
	public void processResponses_test() throws Exception {
		ProcessDetails pd = new  ProcessDetails();
		pd.setBucket("bucket");
		pd.setFileKey("key");
		
		List<StockMovementSvcRespWrapper> responses = new ArrayList<>();
		StockMovementSvcRespWrapper stockMovementSvcRespWrapper = new StockMovementSvcRespWrapper();
		stockMovementSvcRespWrapper.setStatus(ServiceCallStatus.NON_RECOVERABLE);
		
		StockMovementSvcRespWrapper stockMovementSvcRespWrapper1 = new StockMovementSvcRespWrapper();
		stockMovementSvcRespWrapper.setStatus(ServiceCallStatus.RECOVERABLE);
		
		responses.add(stockMovementSvcRespWrapper1);
		responses.add(stockMovementSvcRespWrapper);
		
		Method method = StockDataServiceImpl.class.getDeclaredMethod("processResponses", List.class, String.class,
				String.class, ProcessDetails.class);
		method.setAccessible(true);
		method.invoke(stockDataServiceImpl, responses, "orderId", "sourceId",pd);
		
	}
	
	@Test
	public void processResponses2_test() throws Exception {
		ProcessDetails pd = new  ProcessDetails();
		pd.setBucket("bucket");
		pd.setFileKey("key");
		
		List<StockMovementSvcRespWrapper> responses = new ArrayList<>();
		StockMovementSvcRespWrapper stockMovementSvcRespWrapper = new StockMovementSvcRespWrapper();
		stockMovementSvcRespWrapper.setStatus(ServiceCallStatus.NON_RECOVERABLE);
		
		responses.add(stockMovementSvcRespWrapper);
		
		Method method = StockDataServiceImpl.class.getDeclaredMethod("processResponses", List.class, String.class,
				String.class, ProcessDetails.class);
		method.setAccessible(true);
		method.invoke(stockDataServiceImpl, responses, "orderId", "sourceId",pd);
		
	}
}
