package com.morrisons.wholesale.dsd.service.impl;

import org.junit.Test;
import org.mockito.Mockito;

import com.morrisons.wholesale.dsd.dao.IStockMovementSTGDAO;

public class PreProcessIncorrectStockDataImplTest {
	
	PreProcessIncorrectStockDataImpl preProcessIncorrectStockDataImpl;
		
	@Test
	public void test() {
		IStockMovementSTGDAO stockMovementSTGDAO = Mockito.mock(IStockMovementSTGDAO.class);
		preProcessIncorrectStockDataImpl = new PreProcessIncorrectStockDataImpl(null, stockMovementSTGDAO , null);
		preProcessIncorrectStockDataImpl.fetch(null);
		preProcessIncorrectStockDataImpl.delete(null);
		preProcessIncorrectStockDataImpl.getOperationName();
	}

}
