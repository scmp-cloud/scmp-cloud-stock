package org.macula.cloud.stock.feign;

import org.macula.cloud.stock.exception.StockException;
import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StockFeignClientFallbackFactory implements FallbackFactory<StockFeignClient> {

	@Override
	public StockFeignClient create(Throwable cause) {
		log.error("Call StockFeignClient error:", cause);
		return new StockFeignClient() {

			@Override
			public ResponseEntity<BalanceResponse> status(BalanceRequest request) {
				throw new StockException(String.format("%s params %s error", "Status", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> reserve(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "Reserve", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> commit(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "Commit", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> release(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "Release", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> fulfillment(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "Fulfillment", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> increase(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "Increase", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> reduce(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "Reduce", request), cause);
			}

			@Override
			public ResponseEntity<BalanceResponse> refresh(BalanceRequest request) {
				throw new StockException(String.format("%s params %s error", "Refresh", request), cause);
			}

			@Override
			public ResponseEntity<Boolean> deleteRefreshRequest(String request) {
				throw new StockException(String.format("%s params %s error", "Refresh", request), cause);
			}

			@Override
			public ResponseEntity<Boolean> deleteIncreaseRequest(String request) {
				throw new StockException(String.format("%s params %s error", "Refresh", request), cause);
			}

			@Override
			public ResponseEntity<Boolean> deleteReduceRequest(String request) {
				throw new StockException(String.format("%s params %s error", "Refresh", request), cause);
			}

			@Override
			public ResponseEntity<Boolean> deleteCommitRequest(String request) {
				throw new StockException(String.format("%s params %s error", "Refresh", request), cause);
			}

			@Override
			public ResponseEntity<Boolean> deleteReleaseRequest(String request) {
				throw new StockException(String.format("%s params %s error", "Refresh", request), cause);
			}

			@Override
			public ResponseEntity<Boolean> dayEnd(String yesterday, String today) {
				throw new StockException(String.format("%s params %s -> %s error", "DayEnd", yesterday, today), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> updateRouteStockCode(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "UpdateRouteStockCode", request), cause);
			}

			@Override
			public ResponseEntity<InventoryResponse> updateFulfillmentStockCode(InventoryRequest request) {
				throw new StockException(String.format("%s params %s error", "UpdateFulfillmentStockCode", request), cause);
			}

		};
	}

}
