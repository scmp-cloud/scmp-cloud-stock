package org.macula.cloud.stock.executor;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RebuildRdcStockEventListenerExecutor implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		log.info("ApplicationEvent: {}", event);
	}

}
