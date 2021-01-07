package org.macula.cloud.stock.feign;

import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "scmp-cloud-stock-api", path = "/api/scmp/stock", fallbackFactory = StockFeignClientFallbackFactory.class)
public interface StockFeignClient {

	@GetMapping(value = "/status")
	ResponseEntity<BalanceResponse> status(@RequestBody BalanceRequest request);

	@PostMapping(value = "/reserve")
	ResponseEntity<InventoryResponse> reserve(@RequestBody InventoryRequest request);

	@PostMapping(value = "/commit")
	ResponseEntity<InventoryResponse> commit(@RequestBody InventoryRequest request);

	@PostMapping(value = "/release")
	ResponseEntity<InventoryResponse> release(@RequestBody InventoryRequest request);

	@PostMapping(value = "/fulfillment")
	ResponseEntity<InventoryResponse> fulfillment(@RequestBody InventoryRequest request);

	@PutMapping(value = "/increase")
	ResponseEntity<InventoryResponse> increase(@RequestBody InventoryRequest request);

	@PutMapping(value = "/reduce")
	ResponseEntity<InventoryResponse> reduce(@RequestBody InventoryRequest request);

	@PutMapping(value = "/refresh")
	ResponseEntity<BalanceResponse> refresh(@RequestBody BalanceRequest request);

	@DeleteMapping(value = "/refresh/{request}")
	ResponseEntity<Boolean> deleteRefreshRequest(@PathVariable("request") String request);

	@DeleteMapping(value = "/increase/{request}")
	ResponseEntity<Boolean> deleteIncreaseRequest(@PathVariable("request") String request);

	@DeleteMapping(value = "/reduce/{request}")
	ResponseEntity<Boolean> deleteReduceRequest(@PathVariable("request") String request);

	@DeleteMapping(value = "/commit/{request}")
	ResponseEntity<Boolean> deleteCommitRequest(@PathVariable("request") String request);

	@DeleteMapping(value = "/release/{request}")
	ResponseEntity<Boolean> deleteReleaseRequest(@PathVariable("request") String request);

	@PostMapping(value = "/dayend")
	ResponseEntity<Boolean> dayEnd(@RequestParam("yesterday") String yesterday, @RequestParam("today") String today);

	@GetMapping(value = "/updateRouteStockCode")
	public ResponseEntity<InventoryResponse> updateRouteStockCode(@RequestBody InventoryRequest request);

	@GetMapping(value = "/updateFulfillmentStockCode")
	public ResponseEntity<InventoryResponse> updateFulfillmentStockCode(@RequestBody InventoryRequest request);

}
