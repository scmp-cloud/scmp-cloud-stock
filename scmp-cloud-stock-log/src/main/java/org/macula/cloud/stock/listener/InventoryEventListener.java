package org.macula.cloud.stock.listener;

import org.macula.cloud.stock.event.InventoryCommitEvent;
import org.macula.cloud.stock.event.InventoryFulfillmentEvent;
import org.macula.cloud.stock.event.InventoryIncreaseEvent;
import org.macula.cloud.stock.event.InventoryReduceEvent;
import org.macula.cloud.stock.event.InventoryRefreshEvent;
import org.macula.cloud.stock.event.InventoryReleaseEvent;
import org.macula.cloud.stock.event.InventoryReserveEvent;
import org.macula.cloud.stock.service.InventoryPersistanceService;
import org.macula.cloud.stock.stream.StockChannel;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class InventoryEventListener {

	private InventoryPersistanceService service;

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryIncreaseEvent'")
	public void inventoryIncreaseListening(@Payload InventoryIncreaseEvent event) {
		log.info("监听到事件: InventoryIncreaseEvent -> " + event);
		service.handleInventoryIncreaseEvent(event);
	}

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryReduceEvent'")
	public void inventoryReduceListening(@Payload InventoryReduceEvent event) {
		log.info("监听到事件: InventoryReduceEvent -> " + event);
		service.handleInventoryReduceEvent(event);
	}

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryReserveEvent'")
	public void inventoryReserveListening(@Payload InventoryReserveEvent event) {
		log.info("监听到事件: InventoryReserveEvent -> " + event);
		service.handleInventoryReserveEvent(event);
	}

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryCommitEvent'")
	public void inventoryCommitListening(@Payload InventoryCommitEvent event) {
		log.info("监听到事件: InventoryCommitEvent -> " + event);
		service.handleInventoryCommitEvent(event);
	}

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryFulfillmentEvent'")
	public void inventoryFufillmentListening(@Payload InventoryFulfillmentEvent event) {
		log.info("监听到事件: InventoryFulfillmentEvent -> " + event);
		service.handleInventoryFulfillmentEvent(event);
	}

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryReleaseEvent'")
	public void inventoryReleaseListening(@Payload InventoryReleaseEvent event) {
		log.info("监听到事件: InventoryReleaseEvent -> " + event);
		service.handleInventoryReleaseEvent(event);
	}

	@StreamListener(value = StockChannel.STOCK_INPUT, condition = "headers['rocketmq_TAGS'] == 'InventoryRefreshEvent'")
	public void inventoryRefreshListening(@Payload InventoryRefreshEvent event) {
		log.info("监听到事件: InventoryRefreshEvent -> " + event);
		service.handleInventoryRefreshEvent(event);
	}
}
