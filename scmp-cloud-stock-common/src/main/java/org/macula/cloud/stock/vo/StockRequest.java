package org.macula.cloud.stock.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class StockRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文档索引号(如PO单号)
	 */
	private String document;

	/**
	 * 请求版本号(如PO最后修改时间)
	 */
	private String version;

	/**
	 * 终端
	 */
	private String clientId;

	/**
	 * 渠道
	 */
	private String channel;

	/**
	 * 请求创建时间
	 */
	private long timestamp = System.currentTimeMillis();

	/**
	 * 库存日结天
	 */
	private String accDate;

}
