package org.macula.cloud.stock.test;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.macula.cloud.stock.StockApiApplication;
import org.macula.cloud.stock.service.InventoryCenterService;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;
import org.macula.cloud.stock.vo.ProductQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { StockApiApplication.class })
public class FulfillmentInventoryRequestTest {

	@Autowired
	private InventoryCenterService inventoryService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testFulfillmentRequest() {
		InventoryResponse response;
		InventoryRequest inventoryRequest = InventoryRequest.of("local", "test", "P001", Thread.currentThread().getId() + "$" + System.nanoTime());
		inventoryRequest.setAccDate("20201013");
		inventoryRequest.setDeliveryType("05");

		ProductQuantity pq1 = new ProductQuantity();
		pq1.setProductCode("18013-02");
		pq1.setApplyWhCode("HC02");
		pq1.setApplyLocCode("1001");
		pq1.setApplyQuantity(new BigDecimal(3));
		pq1.setActuallyWhCode("S21");
		pq1.setActuallyLocCode("1001");
		pq1.setActuallyQuantity(new BigDecimal(3));
		inventoryRequest.addProductQuantity(pq1);

		response = inventoryService.fulfillment(inventoryRequest);
		System.out.println("Fulfillment: " + getJSONString(response));
	}

	protected String getJSONString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return String.valueOf(object);
		}
	}
}
