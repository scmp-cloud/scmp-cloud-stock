package org.macula.cloud.stock.controller;

import org.macula.cloud.stock.service.InventoryCenterService;
import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/scmp/stock")
@Tag(name = "库存中心服务")
public class InventoryFeignController {

	private InventoryCenterService inventoryService;

	@GetMapping(value = "/status")
	@Operation(summary = "库存状态")
	public ResponseEntity<BalanceResponse> inventoryStatus(@RequestBody BalanceRequest request) {
		BalanceResponse response = inventoryService.status(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryStatus Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/reserve")
	@Operation(summary = "库存预留")
	public ResponseEntity<InventoryResponse> inventoryReserve(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.reserve(request);
		if (log.isDebugEnabled()) {
			log.debug("inventoryReserve Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/commit")
	@Operation(summary = "库存确认")
	public ResponseEntity<InventoryResponse> inventoryCommit(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.commit(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryCommit Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/release")
	@Operation(summary = "库存释放")
	public ResponseEntity<InventoryResponse> inventoryRelease(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.release(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryRelease Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/fulfillment")
	@Operation(summary = "库存履行")
	public ResponseEntity<InventoryResponse> inventoryFulfillment(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.fulfillment(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryFulfillment Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/increase")
	@Operation(summary = "库存增加")
	public ResponseEntity<InventoryResponse> inventoryIncrease(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.increase(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryRelease Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/reduce")
	@Operation(summary = "库存减少")
	public ResponseEntity<InventoryResponse> inventoryReduce(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.reduce(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryRelease Request {},  Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/refresh")
	@Operation(summary = "库存刷新")
	public ResponseEntity<BalanceResponse> inventoryRefresh(@RequestBody BalanceRequest request) {
		BalanceResponse response = inventoryService.refresh(request);
		if (log.isDebugEnabled()) {
			log.debug("InventoryRefresh Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/refresh/{request}")
	@Operation(summary = "删除库存刷新请求")
	public ResponseEntity<Boolean> deleteRefreshRequest(@PathVariable("request") String request) {
		boolean response = inventoryService.deleteRefreshRequest(request);
		if (log.isDebugEnabled()) {
			log.debug("deleteRefreshRequest Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/increase/{request}")
	@Operation(summary = "删除库存增加请求")
	public ResponseEntity<Boolean> deleteIncreaseRequest(@PathVariable("request") String request) {
		boolean response = inventoryService.deleteIncreaseRequest(request);
		if (log.isDebugEnabled()) {
			log.debug("deleteIncreaseRequest Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/reduce/{request}")
	@Operation(summary = "删除库存减少请求")
	public ResponseEntity<Boolean> deleteReduceRequest(@PathVariable("request") String request) {
		boolean response = inventoryService.deleteReduceRequest(request);
		if (log.isDebugEnabled()) {
			log.debug("deleteReduceRequest Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/commit/{request}")
	@Operation(summary = "删除库存确认请求")
	public ResponseEntity<Boolean> deleteCommitRequest(@PathVariable("request") String request) {
		boolean response = inventoryService.deleteCommitRequest(request);
		if (log.isDebugEnabled()) {
			log.debug("deleteCommitRequest Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/release/{request}")
	@Operation(summary = "删除库存释放请求")
	public ResponseEntity<Boolean> deleteReleaseRequest(@PathVariable("request") String request) {
		boolean response = inventoryService.deleteReleaseRequest(request);
		if (log.isDebugEnabled()) {
			log.debug("deleteReleaseRequest Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/dayend")
	@Operation(summary = "库存日结")
	public ResponseEntity<Boolean> inventoryDayEnd(@RequestParam("yesterday") String yesterday, @RequestParam("today") String today) {
		boolean response = inventoryService.dayEnd(yesterday, today);
		if (log.isDebugEnabled()) {
			log.debug("InventoryDayEnd Request {} -> {} , Response: {}", yesterday, today, response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/updateRouteStockCode")
	@Operation(summary = "更新路由目标库存")
	public ResponseEntity<InventoryResponse> updateRouteStockCode(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.updateRouteStockCode(request);
		if (log.isDebugEnabled()) {
			log.debug("UpdateRouteStockCode Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/updateFulfillmentStockCode")
	@Operation(summary = "更新履行目标库存")
	public ResponseEntity<InventoryResponse> updateFulfillmentStockCode(@RequestBody InventoryRequest request) {
		InventoryResponse response = inventoryService.updateFulfillmentStockCode(request);
		if (log.isDebugEnabled()) {
			log.debug("UpdateFulfillmentStockCode Request {} , Response: {}", request, response);
		}
		return ResponseEntity.ok(response);
	}
}
