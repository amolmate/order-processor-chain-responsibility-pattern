package com.morrisons.wholesale.dsd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGErrorDAO;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGRetryDAO;
import com.morrisons.wholesale.dsd.endpoint.IBasePostEndPoint;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.model.external.stock.CancelMovementRequestDTO;
import com.morrisons.wholesale.dsd.model.external.stock.GenerateDistributionOrderRequestDTO;
import com.morrisons.wholesale.dsd.model.external.stock.StockMovement;
import com.morrisons.wholesale.dsd.threadexecutor.ThreadExecutor;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;
import com.morrisons.wholesale.dsd.vo.ServiceCallStatus;

@RunWith(MockitoJUnitRunner.class)
public class StockMovementServiceTest {

	@InjectMocks
	private StockMovementService stockMovementService;

	@Mock
	private IWMMExceptionFactory exceptionFactory;

	@Mock
	private WMMException exception;

	@Mock
	private ThreadExecutor<ServiceCallStatus> executor;

	@Mock
	private IBasePostEndPoint<CancelMovementRequestDTO, Response> cancelStockMovementEndPoint;

	@Mock
	private IBasePostEndPoint<GenerateDistributionOrderRequestDTO, Response> generateDistributionOrderEndPoint;

	@Mock
	private GenerateDistributionOrderRequestDTO generateDistributionOrderRequestDTO;

	private StockMovement stockMovement;

	@Before
	public void setUp() {

		Mockito.when(exceptionFactory.createException(Matchers.anyInt(), Matchers.any(Throwable.class)))
				.thenReturn(exception);

		Mockito.when(exceptionFactory.createException(Matchers.anyInt(), Matchers.anyString())).thenReturn(exception);

		stockMovement = new StockMovement();
		stockMovement.setDestinationLocationId("214");
		stockMovement.setOrderId("634");
	}

	@Test
	public void testCancelStockMovement() {

		ProcessDetails pd = new ProcessDetails();
		pd.setFileKey("/src/bucket/filename.bat");
		Mockito.when(cancelStockMovementEndPoint.post(Matchers.any(), Matchers.any())).thenReturn(null);
		stockMovementService.cancelStockMovement("orderid-1", "2", pd);

	}

	@Test
	public void testGenerateDistributionOrder() {

		Mockito.when(generateDistributionOrderEndPoint.post(Matchers.any(), Matchers.any())).thenReturn(null);
		stockMovementService.generateDistributionOrder("orderid-1", "2", new ProcessDetails());
	}

	@Test

	public void testGenerateDistributionOrder_throws_runtime_exception() {

		ApplicationConfig applicationConfig = Mockito.mock(ApplicationConfig.class);
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);
		IStockMovementSTGRetryDAO stockMovementSTGRetryDAO = Mockito.mock(IStockMovementSTGRetryDAO.class);
		StockMovementService stockMovementService = new StockMovementService(generateDistributionOrderEndPoint,
				cancelStockMovementEndPoint, exceptionFactory, applicationConfig, stockMovementSTGErrorDAO,
				stockMovementSTGRetryDAO);

		Mockito.when(generateDistributionOrderEndPoint.post(Matchers.any(), Matchers.any()))
				.thenThrow(WMMException.class);
		applicationConfig.getRecoverableResponseCode();
		stockMovementService.generateDistributionOrder("orderid-1", "2", new ProcessDetails());
	}

	@Test
	public void testGenerateDistributionOrder_throws_runtime_exception1() {
		ProcessDetails processDetails = new ProcessDetails();
		processDetails.setBucket("bucket");
		processDetails.setFileKey("key");
		ApplicationConfig applicationConfig = Mockito.mock(ApplicationConfig.class);
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);
		IStockMovementSTGRetryDAO stockMovementSTGRetryDAO = Mockito.mock(IStockMovementSTGRetryDAO.class);
		StockMovementService stockMovementService = new StockMovementService(generateDistributionOrderEndPoint,
				cancelStockMovementEndPoint, exceptionFactory, applicationConfig, stockMovementSTGErrorDAO,
				stockMovementSTGRetryDAO);

		Mockito.when(generateDistributionOrderEndPoint.post(Matchers.any(), Matchers.any()))
				.thenThrow(WMMException.class);

		WMMException we = Mockito.mock(WMMException.class);
		Mockito.when(exceptionFactory.createException(Mockito.anyInt(), Mockito.anyString())).thenReturn(we);
		Mockito.when(we.getMessage()).thenReturn("runtime exception");
		List<Integer> value = new ArrayList<>();
		value.add(0);
		Mockito.when(applicationConfig.getRecoverableResponseCode()).thenReturn(value);
		stockMovementService.generateDistributionOrder("orderid-1", "2", processDetails);
	}

	@Test(expected = WMMException.class)
	public void testGenerateDistributionOrder_throws_exception() {
		ProcessDetails processDetails = new ProcessDetails();
		processDetails.setBucket("bucket");
		processDetails.setFileKey("key");
		ApplicationConfig applicationConfig = Mockito.mock(ApplicationConfig.class);
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);
		IStockMovementSTGRetryDAO stockMovementSTGRetryDAO = Mockito.mock(IStockMovementSTGRetryDAO.class);
		StockMovementService stockMovementService = new StockMovementService(null, cancelStockMovementEndPoint,
				exceptionFactory, applicationConfig, stockMovementSTGErrorDAO, stockMovementSTGRetryDAO);

		// Mockito.when(generateDistributionOrderEndPoint.post(Matchers.any(),
		// Matchers.any())).thenThrow(WMMException.class);

		WMMException we = Mockito.mock(WMMException.class);

		Mockito.when(exceptionFactory.createException(Mockito.anyInt(), Mockito.anyString(),Mockito.any(Throwable.class))).thenReturn(we);
		Mockito.when(we.getMessage()).thenReturn("runtime exception");
		List<Integer> value = new ArrayList<>();
		value.add(0);
		Mockito.when(applicationConfig.getRecoverableResponseCode()).thenReturn(value);
		stockMovementService.generateDistributionOrder("orderid-1", "2", processDetails);
	}

	// ------------------------------------------------------------------------

	@Test
	public void testcancelStockMovement_throws_runtime_exception() {
		ProcessDetails pd = new ProcessDetails();
		pd.setFileKey("/src/bucket/filename.bat");
		ApplicationConfig applicationConfig = Mockito.mock(ApplicationConfig.class);
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);
		IStockMovementSTGRetryDAO stockMovementSTGRetryDAO = Mockito.mock(IStockMovementSTGRetryDAO.class);
		StockMovementService stockMovementService = new StockMovementService(generateDistributionOrderEndPoint,
				cancelStockMovementEndPoint, exceptionFactory, applicationConfig, stockMovementSTGErrorDAO,
				stockMovementSTGRetryDAO);

		Mockito.when(cancelStockMovementEndPoint.post(Matchers.any(), Matchers.any())).thenThrow(WMMException.class);
		applicationConfig.getRecoverableResponseCode();
		stockMovementService.cancelStockMovement("orderid-1", "2", pd);
	}

	@Test
	public void testcancelStockMovement_throws_runtime_exception1() {
		ProcessDetails processDetails = new ProcessDetails();
		processDetails.setBucket("bucket");
		processDetails.setFileKey("key");
		ApplicationConfig applicationConfig = Mockito.mock(ApplicationConfig.class);
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);
		IStockMovementSTGRetryDAO stockMovementSTGRetryDAO = Mockito.mock(IStockMovementSTGRetryDAO.class);
		StockMovementService stockMovementService = new StockMovementService(generateDistributionOrderEndPoint,
				cancelStockMovementEndPoint, exceptionFactory, applicationConfig, stockMovementSTGErrorDAO,
				stockMovementSTGRetryDAO);

		Mockito.when(cancelStockMovementEndPoint.post(Matchers.any(), Matchers.any())).thenThrow(WMMException.class);

		WMMException we = Mockito.mock(WMMException.class);
		Mockito.when(exceptionFactory.createException(Mockito.anyInt(), Mockito.anyString())).thenReturn(we);
		Mockito.when(we.getMessage()).thenReturn("runtime exception");
		List<Integer> value = new ArrayList<>();
		value.add(0);
		Mockito.when(applicationConfig.getRecoverableResponseCode()).thenReturn(value);
		stockMovementService.cancelStockMovement("orderid-1", "2", processDetails);
	}

	@Test(expected = WMMException.class)
	public void testcancelStockMovement_throws_exception() {
		ProcessDetails processDetails = new ProcessDetails();
		processDetails.setBucket("bucket");
		processDetails.setFileKey("key");
		ApplicationConfig applicationConfig = Mockito.mock(ApplicationConfig.class);
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);
		IStockMovementSTGRetryDAO stockMovementSTGRetryDAO = Mockito.mock(IStockMovementSTGRetryDAO.class);
		StockMovementService stockMovementService = new StockMovementService(null, null, exceptionFactory,
				applicationConfig, stockMovementSTGErrorDAO, stockMovementSTGRetryDAO);

		// Mockito.when(generateDistributionOrderEndPoint.post(Matchers.any(),
		// Matchers.any())).thenThrow(WMMException.class);

		WMMException we = Mockito.mock(WMMException.class);

		Mockito.when(exceptionFactory.createException(Mockito.anyInt(), Mockito.anyString(),Mockito.any(Throwable.class))).thenReturn(we);
		Mockito.when(we.getMessage()).thenReturn("runtime exception");
		List<Integer> value = new ArrayList<>();
		value.add(0);
		Mockito.when(applicationConfig.getRecoverableResponseCode()).thenReturn(value);
		stockMovementService.cancelStockMovement("orderid-1", "2", processDetails);
	}
}