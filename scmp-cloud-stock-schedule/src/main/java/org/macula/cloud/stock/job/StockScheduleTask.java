//package org.macula.cloud.stock.job;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.macula.cloud.core.utils.SystemUtils;
//import org.macula.cloud.stock.service.StockScheduleService;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import io.choerodon.asgard.schedule.annotation.JobTask;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class StockScheduleTask {
//
//	private StockScheduleService stockService;
//
//	@Scheduled(initialDelay = 10000L, fixedRate = Long.MAX_VALUE)
//	public Map<String, Object> doStockDailyEndSchedule() {
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("accDate", "20201013");
//		return this.doStockDailyEndSchedule(data);
//	}
//
//	/**
//	* 定时任务: 执行日结任务
//	* @param data
//	*/
//	@JobTask(code = "stock-daily-end-schedulel", description = "Stock定时更新Redis库存、Route及Fulfillment信息")
//	public Map<String, Object> doStockDailyEndSchedule(Map<String, Object> data) {
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("Start 定时执行Stock日结任务....");
//			}
//			String accDate = (String) data.get("accDate");
//			if (accDate == null) {
//				Instant instant = SystemUtils.getCurrentInstant().plus(8, ChronoUnit.HOURS);
//				accDate = DateFormatUtils.format(new Date(instant.toEpochMilli()), "yyyyMMdd");
//			}
//			if (log.isInfoEnabled()) {
//				log.info("  Stock日结{}", accDate);
//			}
//			int count = 0;
//			//			stockService.deleteStockStatus(accDate);
//			stockService.deleteGroupStock(accDate);
//			count += stockService.loadRdcStock(accDate);
//			count += stockService.updateProductGroupStock(accDate);
//			count += stockService.updateDefaultGroupStock(accDate);
//			stockService.unloadStock2Redis(accDate);
//			stockService.loadStock2Redis(accDate);
//			stockService.updateGroupPath(accDate);
//			stockService.unloadGroup2Redis(accDate);
//			stockService.loadGroup2Redis(accDate);
//			stockService.unloadRoute2Redis(accDate);
//			stockService.loadRoute2Redis(accDate);
//			if (log.isInfoEnabled()) {
//				log.info("End 定时执行Stock日结任务:  {}", count);
//			}
//		} catch (Throwable ex) {
//			log.error("定时执行Stock日结任务！", ex);
//		}
//		return data;
//	}
//
//}
