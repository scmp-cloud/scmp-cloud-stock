package org.macula.cloud.stock.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class StockResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 执行返回代码
	 */
	private String code;

	/**
	 * 执行返回信息
	 */
	private String message;

	/**
	 * 执行返回时间
	 */
	private long timestamp = System.currentTimeMillis();

}
