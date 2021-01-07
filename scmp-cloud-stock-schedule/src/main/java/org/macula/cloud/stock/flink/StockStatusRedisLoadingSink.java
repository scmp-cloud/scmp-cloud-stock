package org.macula.cloud.stock.flink;

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.macula.cloud.stock.domain.StockStatus;
import org.macula.cloud.stock.feign.StockFeignClient;
import org.macula.cloud.stock.repository.StockStatusRepository;
import org.macula.cloud.stock.vo.BalanceQuantity;
import org.macula.cloud.stock.vo.BalanceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockStatusRedisLoadingSink extends RichSinkFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	private static transient StockStatusRepository stockStatusRepository;

	private static transient StockFeignClient stockClient;

	private static transient long start = Long.MAX_VALUE;

	private static transient long end = Long.MIN_VALUE;

	private static transient long count = 0;

	private String accDate;

	public StockStatusRedisLoadingSink(String accDate) {
		this.accDate = accDate;
	}

	public static void initializeBeans(StockStatusRepository repository, StockFeignClient client) {
		if (stockStatusRepository == null && repository != null) {
			stockStatusRepository = repository;
		}
		if (stockClient == null && client != null) {
			stockClient = client;
		}
	}

	@Override
	public void invoke(PageRequest pageRequest, @SuppressWarnings("rawtypes") Context context) throws Exception {
		Assert.notNull(stockStatusRepository, "Please initial StockStatusRepository first!");
		Assert.notNull(stockClient, "Please initial StockFeignClient first!");

		log.info("Processing PageRequest {}", pageRequest);

		long now = System.currentTimeMillis();
		if (now < start) {
			start = now;
		}

		Page<StockStatus> stockStatusList = stockStatusRepository.findAccDateStock(accDate, pageRequest);

		count += stockStatusList.getNumberOfElements();

		BalanceRequest request = BalanceRequest.of("STOCK-JOB", "db2Redis", "Page" + pageRequest.getPageNumber(),
				String.valueOf(System.currentTimeMillis()));
		request.setAccDate(accDate);

		for (StockStatus status : stockStatusList) {
			BalanceQuantity balance = new BalanceQuantity();
			balance.setProductCode(status.getProductCode());
			balance.setWhCode(status.getWhCode());
			balance.setLocCode(status.getLocCode());
			balance.setInitializedQty(status.getInitializedQty());
			balance.setIncreasedQty(status.getIncreasedQty());
			balance.setReducedQty(status.getReducedQty());
			balance.setBalanceQty(status.getBalanceQty());
			balance.setReservedQty(status.getReservedQty());
			balance.setCommitedQty(status.getCommitedQty());
			balance.setReleasedQty(status.getReservedQty());
			balance.setFulfillmentQty(status.getFulfillmentQty());

			request.addInventoryBalance(balance);
		}

		while (true) {
			try {
				stockClient.refresh(request);
				break;
			} catch (Exception ex) {
				log.error("StockFeignClient refresh error:", ex);
			}
		}

		now = System.currentTimeMillis();
		if (now > end) {
			end = now;
			long used = end - start;
			if (used > 0) {
				log.info("Complete stock to Redis, total: {}, used: {}, tps: {}", count, used, count * 1000 / used);
			}
		}
	}

}
