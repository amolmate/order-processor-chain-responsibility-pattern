package com.morrisons.wholesale.dsd.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dao.impl.StockMovementSTGDAOImpl;
import com.morrisons.wholesale.dsd.endpoint.IBasePostEndPoint;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.model.external.stock.StockMovement;
import com.morrisons.wholesale.dsd.service.ITransformer;
import com.morrisons.wholesale.dsd.vo.StockMovementWrapper;

@RunWith(MockitoJUnitRunner.class)
public class FetchStockFromDBThreadTest {

	@InjectMocks
	private ProcessStockMovementThread fetchStockFromDBThread;

	@Mock
	private StockMovementWrapper stockMoveWrapper;

	@Mock
	private StockMovementSTGDAOImpl stockMovementSTGDAO;

	@Mock
	private Date expectedArrival;

	@Mock
	private ITransformer transformer;

	@Mock
	private IBasePostEndPoint<StockMovement, Response> stockMovementServiceEndPoint;

	@Mock
	private StockMovement stockMovement;

	@Mock
	private Exception exception;
	
	@Mock
	private WMMException wmmException;
	
	@Mock
	private ApplicationConfig applicationConfig;

	@Test
	public void testCallWithoutException() throws Exception {

		Mockito.when(transformer.transform(Matchers.any(), Matchers.any())).thenReturn(null);
		fetchStockFromDBThread.call();
	}
	
	@Test
	public void testCallWithException() throws Exception {

		Mockito.when(transformer.transform(Matchers.any(), Matchers.any())).thenThrow(wmmException);
		fetchStockFromDBThread.call();
	}
	
	@Test
	public void testCallWithExceptionBranchCoverage() throws Exception {
		
		List<Integer>responseCodes=new ArrayList<>();
		responseCodes.add(500);
		responseCodes.add(400);
		responseCodes.add(0);

		Mockito.when(transformer.transform(Matchers.any(), Matchers.any())).thenThrow(wmmException);
		
		Mockito.when(applicationConfig.getRecoverableResponseCode()).thenReturn(responseCodes);
		
		fetchStockFromDBThread.call();
	}
}