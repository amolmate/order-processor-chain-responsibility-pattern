package com.morrisons.wholesale.dsd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.morrisons.wholesale.dsd.dao.IStockMovementSTGDAO;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGErrorDAO;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

public class BasePreProcessStockDataTest {
	
	
	BasePreProcessStockData basePreProcessStockData;
	
	BasePreProcessStockData basePreProcessStockData1;
	

	
	
	@Before
	public void init() {
		
		IWMMExceptionFactory exceptionFactory = Mockito.mock(IWMMExceptionFactory.class);

		
		IStockMovementSTGDAO stockMovementSTGDAO = Mockito.mock(IStockMovementSTGDAO.class);;

		
		IStockMovementSTGErrorDAO stockMovementSTGErrorDAO = Mockito.mock(IStockMovementSTGErrorDAO.class);;
		
		basePreProcessStockData= new PreProcessDuplicateStockDataImpl(exceptionFactory,stockMovementSTGDAO,stockMovementSTGErrorDAO) {
			
			protected List<Object[]> fetch(Long jobId){
				List<Object[]> list = new ArrayList<>();
				Object[] array = new Object[2];
				array[0]="abc";
				array[1]="abc";
				
				Object[] array1 = new Object[2];
				array1[0]="abc";
				array1[1]="abc";
				
				list.add(array1);
				list.add(array);
				return list;
			}
			
			protected Integer delete(Long jobId){
				return 1;
			}
			
			protected String getOperationName() {
				return "ani";
			}
		};
		
		basePreProcessStockData1= new PreProcessDuplicateStockDataImpl(exceptionFactory,stockMovementSTGDAO,stockMovementSTGErrorDAO) {
			
			protected List<Object[]> fetch(Long jobId){
				List<Object[]> list = new ArrayList<>();
				return list;
			}
			
			protected Integer delete(Long jobId){
				return 1;
			}
			
			protected String getOperationName() {
				return "ani";
			}
		};
	}

	@Test
	public void test() {
		basePreProcessStockData.process(1l,"orderId", "");
		basePreProcessStockData1.process(1l,"orderId", "");
	}

}
