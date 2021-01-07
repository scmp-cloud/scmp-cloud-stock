package org.macula.cloud.stock.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StockChannel {

	final String STOCK_INPUT = "stock-input";

	@Input(StockChannel.STOCK_INPUT)
	SubscribableChannel stockChannel();
}
