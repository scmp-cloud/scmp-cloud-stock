package org.macula.cloud.stock.flink;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.macula.cloud.stock.repository.StockStatusRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

public class StockStatusRedisLoadingSource extends RichSourceFunction<PageRequest> {

	private static final long serialVersionUID = 1L;

	private static transient StockStatusRepository statusRepository;

	private static int size = 100;
	private String accDate;

	public StockStatusRedisLoadingSource(String accDate) {
		this.accDate = accDate;
	}

	public static void initializeBeans(StockStatusRepository repository) {
		if (statusRepository == null && repository != null) {
			statusRepository = repository;
		}
	}

	@Override
	public void run(SourceContext<PageRequest> ctx) throws Exception {
		Assert.notNull(statusRepository, "Please initial statusRepository first!");
		long totalCount = statusRepository.findAccDateStock(accDate, PageRequest.of(1, 1)).getTotalElements();
		long pageCount = (totalCount / size) + (totalCount % size != 0 ? 1 : 0);
		for (int i = 0; i < pageCount; i++) {
			ctx.collect(PageRequest.of(i, size, Sort.by("id")));
		}
	}

	@Override
	public void cancel() {

	}

}
