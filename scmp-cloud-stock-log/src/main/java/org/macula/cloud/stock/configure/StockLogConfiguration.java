package org.macula.cloud.stock.configure;

import java.util.function.Consumer;

import org.macula.cloud.stock.event.InventoryCommitEvent;
import org.macula.cloud.stock.event.InventoryFulfillmentEvent;
import org.macula.cloud.stock.event.InventoryIncreaseEvent;
import org.macula.cloud.stock.event.InventoryReduceEvent;
import org.macula.cloud.stock.event.InventoryRefreshEvent;
import org.macula.cloud.stock.event.InventoryReleaseEvent;
import org.macula.cloud.stock.event.InventoryReserveEvent;
import org.macula.cloud.stock.service.InventoryPersistanceService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({ StockConfig.class })
// TODO 使用Function的方式来配置，还没有头绪
public class StockLogConfiguration {

	private InventoryPersistanceService service;

	@Bean
	public Consumer<InventoryIncreaseEvent> onInventoryIncreaseEventListening() {
		return event -> {
			log.info("监听到事件: InventoryIncreaseEvent -> " + event);
			service.handleInventoryIncreaseEvent(event);
		};
	}

	@Bean
	public Consumer<InventoryReduceEvent> onInventoryReduceEventListening() {
		return event -> {
			log.info("监听到事件: InventoryReduceEvent -> " + event);
			service.handleInventoryReduceEvent(event);
		};
	}

	@Bean
	public Consumer<InventoryReserveEvent> onInventoryReserveEventListening() {
		return event -> {
			log.info("监听到事件: InventoryReserveEvent -> " + event);
			service.handleInventoryReserveEvent(event);
		};
	}

	@Bean
	public Consumer<InventoryCommitEvent> onInventoryCommitEventListening() {
		return event -> {
			log.info("监听到事件: InventoryCommitEvent -> " + event);
			service.handleInventoryCommitEvent(event);
		};
	}

	@Bean
	public Consumer<InventoryFulfillmentEvent> onInventoryFulfillmentEventListening() {
		return event -> {
			log.info("监听到事件: InventoryFulfillmentEvent -> " + event);
			service.handleInventoryFulfillmentEvent(event);
		};
	}

	@Bean
	public Consumer<InventoryReleaseEvent> onInventoryReleaseEventListening() {
		return event -> {
			log.info("监听到事件: InventoryReleaseEvent -> " + event);
			service.handleInventoryReleaseEvent(event);
		};
	}

	@Bean
	public Consumer<InventoryRefreshEvent> onInventoryRefreshEventListening() {
		return event -> {
			log.info("监听到事件: InventoryRefreshEvent -> " + event);
			service.handleInventoryRefreshEvent(event);
		};
	}
}
