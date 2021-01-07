package org.macula.cloud.stock.listener;

import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.macula.cloud.stock.event.InventoryEvent;
import org.macula.cloud.stock.stream.StockChannel;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class InventoryEventListeningAdapter {

	private StockChannel channel;

	@Async
	@EventListener
	public void onMesssage(InventoryEvent event) {
		boolean result = false;
		while (!result) {
			try {
				result = channel.stockOutputTopic().send(MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.TAGS, event.getClazz()).build());
			} catch (Exception ex) {
				log.error("send RocketMQ error, ", ex);
			}
		}
	}

}
