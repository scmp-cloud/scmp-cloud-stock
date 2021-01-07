package org.macula.cloud.stock.job;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.macula.cloud.stock.service.StockScheduleService;
import org.macula.cloud.stock.util.SystemUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class StockQuartzSchedule {

	private StockScheduleService stockService;

	/**
	* 定时任务: 执行日结任务
	*/
	@Scheduled(cron = "5 10 1 * * ?")
	public void doStockDailyEndSchedule() {
		try {
			if (log.isInfoEnabled()) {
				log.info("Start 定时执行Stock日结任务....");
			}
			Instant instant = SystemUtils.getCurrentInstant().plus(8, ChronoUnit.HOURS);
			String accDate = DateFormatUtils.format(new Date(instant.toEpochMilli()), "yyyyMMdd");
			if (log.isInfoEnabled()) {
				log.info("  Stock日结{}", accDate);
			}
			int count = 0;
			stockService.deleteStockStatus(accDate);
			stockService.deleteGroupStock(accDate);
			count += stockService.loadRdcStock(accDate);
			count += stockService.updateProductGroupStock(accDate);
			count += stockService.updateDefaultGroupStock(accDate);
			stockService.unloadStock2Redis(accDate);
			stockService.loadStock2Redis(accDate);
			stockService.updateGroupPath(accDate);
			stockService.unloadGroup2Redis(accDate);
			stockService.loadGroup2Redis(accDate);
			stockService.unloadRoute2Redis(accDate);
			stockService.loadRoute2Redis(accDate);
			if (log.isInfoEnabled()) {
				log.info("End 定时执行Stock日结任务:  {}", count);
			}
		} catch (Throwable ex) {
			log.error("定时执行Stock日结任务！", ex);
		}
	}

}
