package org.macula.cloud.stock.test;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.macula.cloud.stock.StockApplicationTest;
import org.macula.cloud.stock.service.InventoryCenterService;
import org.macula.cloud.stock.vo.BalanceQuantity;
import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { StockApplicationTest.class })
public class RefreshInventoryRequestTest {

	@Autowired
	private InventoryCenterService inventoryService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testRefreshRequest() {
		BalanceResponse response;
		BalanceRequest request = BalanceRequest.of("local", "test", "C001", Thread.currentThread().getId() + "$" + System.nanoTime());
		request.setAccDate("20200924");

		BalanceQuantity pq1 = new BalanceQuantity();
		pq1.setProductCode("18002-03");
		pq1.setLocCode("1002");
		pq1.setInitializedQty(new BigDecimal(12345));
		pq1.setBalanceQty(new BigDecimal(12345));
		pq1.setWhCode("HC03");
		request.addInventoryBalance(pq1);

		BalanceQuantity pq2 = new BalanceQuantity();
		pq2.setProductCode("18002-04");
		pq2.setLocCode("1002");
		pq2.setInitializedQty(new BigDecimal(12345));
		pq2.setBalanceQty(new BigDecimal(12345));
		pq2.setWhCode("HC01");
		request.addInventoryBalance(pq2);

		response = inventoryService.refresh(request);
		System.out.println("Refresh: " + getJSONString(response));
		waitForThreads();
	}

	protected void waitForThreads() {
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected String getJSONString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return String.valueOf(object);
		}
	}
}
