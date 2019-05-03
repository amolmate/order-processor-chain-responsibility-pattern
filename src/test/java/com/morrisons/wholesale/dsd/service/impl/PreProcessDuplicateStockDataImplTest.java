package com.morrisons.wholesale.dsd.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.morrisons.wholesale.dsd.dao.IStockMovementSTGDAO;

public class PreProcessDuplicateStockDataImplTest {

	PreProcessDuplicateStockDataImpl preProcessDuplicateStockDataImpl;
	
	@Test
	public void test() {
		IStockMovementSTGDAO stockMovementSTGDAO = Mockito.mock(IStockMovementSTGDAO.class);
		preProcessDuplicateStockDataImpl = new PreProcessDuplicateStockDataImpl(null, stockMovementSTGDAO , null);
		preProcessDuplicateStockDataImpl.fetch(null);
		preProcessDuplicateStockDataImpl.delete(null);
		preProcessDuplicateStockDataImpl.getOperationName();
	}

}
