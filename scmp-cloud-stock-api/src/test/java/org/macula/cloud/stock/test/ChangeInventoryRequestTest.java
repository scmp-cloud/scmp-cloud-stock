package org.macula.cloud.stock.test;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.macula.cloud.stock.StockApplicationTest;
import org.macula.cloud.stock.service.InventoryCenterService;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;
import org.macula.cloud.stock.vo.ProductQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { StockApplicationTest.class })
public class ChangeInventoryRequestTest {

	@Autowired
	private InventoryCenterService inventoryService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testIncreaseRequest() {
		InventoryResponse response;
		InventoryRequest inventoryRequest = InventoryRequest.of("local", "test", "I001", Thread.currentThread().getId() + "$" + System.nanoTime());
		inventoryRequest.setAccDate("20201013");

		ProductQuantity pq1 = new ProductQuantity();
		pq1.setProductCode("18002-03");
		pq1.setApplyLocCode("1001");
		pq1.setApplyQuantity(new BigDecimal(2));
		pq1.setApplyWhCode("HC03");
		inventoryRequest.addProductQuantity(pq1);

		ProductQuantity pq2 = new ProductQuantity();
		pq2.setProductCode("18002-04");
		pq2.setApplyLocCode("1001");
		pq2.setApplyQuantity(new BigDecimal(5));
		pq2.setApplyWhCode("HC01");
		inventoryRequest.addProductQuantity(pq2);

		response = inventoryService.increase(inventoryRequest);
		System.out.println("Increase: " + getJSONString(response));
		waitForThreads();
	}

	@Test
	public void testReduceRequest() {
		InventoryResponse response;
		InventoryRequest inventoryRequest = InventoryRequest.of("local", "test", "I001", Thread.currentThread().getId() + "$" + System.nanoTime());
		inventoryRequest.setAccDate("20201013");

		ProductQuantity pq1 = new ProductQuantity();
		pq1.setProductCode("18002-03");
		pq1.setApplyLocCode("1001");
		pq1.setApplyQuantity(new BigDecimal(2));
		pq1.setApplyWhCode("HC03");
		inventoryRequest.addProductQuantity(pq1);

		ProductQuantity pq2 = new ProductQuantity();
		pq2.setProductCode("18002-04");
		pq2.setApplyLocCode("1001");
		pq2.setApplyQuantity(new BigDecimal(3));
		pq2.setApplyWhCode("HC01");
		inventoryRequest.addProductQuantity(pq2);

		response = inventoryService.reduce(inventoryRequest);
		System.out.println("Reduce: " + getJSONString(response));
		waitForThreads();
	}

	protected String getJSONString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return String.valueOf(object);
		}
	}

	protected void waitForThreads() {
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
