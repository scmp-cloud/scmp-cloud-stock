package org.macula.cloud.stock.exception;

public class StockException extends RuntimeException {

	private static final long serialVersionUID = 1949318937017425443L;

	public StockException(String message) {
		super(message);
	}

	public StockException(String message, Throwable cause) {
		super(message, cause);
	}

}
