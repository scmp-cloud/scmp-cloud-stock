package org.macula.cloud.stock.test;

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
public class StatusInventoryRequestTest {

	@Autowired
	private InventoryCenterService inventoryService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testStatusRequest() {
		BalanceResponse response;
		BalanceRequest inventoryRequest = BalanceRequest.of("local", "test", "I001", Thread.currentThread().getId() + "$" + System.nanoTime());
		inventoryRequest.setAccDate("20201013");

		BalanceQuantity pq1 = new BalanceQuantity();
		pq1.setProductCode("18002-03");
		pq1.setLocCode("1001");
		pq1.setWhCode("HC03");
		inventoryRequest.addInventoryBalance(pq1);

		BalanceQuantity pq2 = new BalanceQuantity();
		pq2.setProductCode("18002-04");
		pq2.setLocCode("1001");
		pq2.setWhCode("HC01");
		inventoryRequest.addInventoryBalance(pq2);

		response = inventoryService.status(inventoryRequest);
		System.out.println("Status: " + getJSONString(response));
	}

	protected String getJSONString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return String.valueOf(object);
		}
	}
}
