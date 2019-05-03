package com.morrisons.wholesale.dsd.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morrisons.wholesale.dsd.entity.StockMovementDetail;
import com.morrisons.wholesale.dsd.model.external.stock.StockMovement;
import com.morrisons.wholesale.dsd.vo.StockMovementWrapper;

@RunWith(MockitoJUnitRunner.class)
public class TransformerTest {

	private final String STOCKMOVEMENTINPUT = "src/test/resources/StockMoveWrapperDetail.json";

	@Spy
	@InjectMocks
	private Transformer transformer;

	@Mock
	private StockMovementWrapper stockMovementWrappersIP;

	@Mock
	private StockMovement stockMovementOP;

	@Test
	public void transformTest() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		StockMovementWrapper stockMovementWrapper = mapper.readValue(new File(STOCKMOVEMENTINPUT),
				StockMovementWrapper.class);

		Assert.assertNotNull("Null value is not returned",
				transformer.transform(stockMovementWrapper, "fdeac0df-14ec-47e6-8223-f965ef5de6f6"));
	}
	
	@Test
	public void test() throws Exception {
		List<StockMovementDetail> stockMovementDetailsIP = new ArrayList<>();
		StockMovementDetail stckMovDetail = new StockMovementDetail();
		stckMovDetail.setCustomerOrderQuantity(new BigDecimal("1"));
		stockMovementDetailsIP.add(stckMovDetail);
		Transformer transformer = new Transformer();
		Method method = transformer.getClass().getDeclaredMethod("createItems", List.class);
		method.setAccessible(true);
		method.invoke(transformer, stockMovementDetailsIP);
	}
}