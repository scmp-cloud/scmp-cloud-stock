package org.macula.cloud.stock.sentinel;

import org.macula.cloud.stock.exception.StockException;

public class SentinelFallbackHandler {

	public static <T> T fallbackException(Throwable throwable) {
		throw new StockException("SentinelFallback occured", throwable);
	}

}
