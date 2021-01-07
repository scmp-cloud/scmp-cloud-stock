package org.macula.cloud.stock.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StockChannel {

	final String STOCK_OUTPUT = "stock-output";

	@Output(StockChannel.STOCK_OUTPUT)
	MessageChannel stockOutputTopic();
}
