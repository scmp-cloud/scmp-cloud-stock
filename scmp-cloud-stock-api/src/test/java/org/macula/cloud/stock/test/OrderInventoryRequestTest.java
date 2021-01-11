package org.macula.cloud.stock.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
public class OrderInventoryRequestTest {

	@Autowired
	private InventoryCenterService inventoryService;
	private ObjectMapper objectMapper = new ObjectMapper();
	private ExecutorService executor = Executors.newFixedThreadPool(100);

	@Test
	public void testSingleRequest() {
		InventoryResponse response;
		InventoryRequest inventoryRequest = InventoryRequest.of("local", "test", "P001", Thread.currentThread().getId() + "$" + System.nanoTime());
		inventoryRequest.setAccDate("20201013");
		inventoryRequest.setDeliveryType("05");

		ProductQuantity pq1 = new ProductQuantity();
		pq1.setProductCode("18002-03");
		pq1.setApplyLocCode("1002");
		pq1.setApplyQuantity(new BigDecimal(3));
		pq1.setApplyWhCode("HC03");
		inventoryRequest.addProductQuantity(pq1);

		ProductQuantity pq2 = new ProductQuantity();
		pq2.setProductCode("18002-04");
		pq2.setApplyLocCode("1002");
		pq2.setApplyQuantity(new BigDecimal(7));
		pq2.setApplyWhCode("HC01");
		inventoryRequest.addProductQuantity(pq2);

		response = inventoryService.reserve(inventoryRequest);
		System.out.println("Reserve: " + getJSONString(response));
		if (response.isSuccess() && response.isActuallySufficient()) {
			response = inventoryService.commit(inventoryRequest);
			System.out.println("Commit: " + getJSONString(response));
		} else {
			response = inventoryService.release(inventoryRequest);
			System.out.println("Release: " + getJSONString(response));
		}
	}

	@Test
	public void testThreadsRequest() {
		List<Future<?>> results = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			results.add(executor.submit(() -> this.testSingleRequest()));
		}
		for (Future<?> result : results) {
			try {
				result.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
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
