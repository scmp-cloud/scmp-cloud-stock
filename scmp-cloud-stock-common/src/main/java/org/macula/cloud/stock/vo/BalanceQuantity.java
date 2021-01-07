package org.macula.cloud.stock.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.macula.cloud.stock.util.InventoryUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BalanceQuantity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 产品SKU
	 */
	private String productCode;

	/**
	 * 货仓代码 
	 */
	private String whCode;

	/**
	 * 产品适用库区
	 */
	private String locCode;

	/**
	 * 初始化数量
	 */
	private BigDecimal initializedQty = BigDecimal.ZERO;

	/**
	 * 可用数量
	 */
	private BigDecimal balanceQty = BigDecimal.ZERO;

	/**
	 * 预留数量
	 */
	private BigDecimal reservedQty = BigDecimal.ZERO;

	/**
	 * 释放数量
	 */
	private BigDecimal releasedQty = BigDecimal.ZERO;
	/**
	 * 已使用数量
	 */
	private BigDecimal commitedQty = BigDecimal.ZERO;

	/**
	 * 增加数量
	 */
	private BigDecimal increasedQty = BigDecimal.ZERO;

	/**
	 * 减少数量
	 */
	private BigDecimal reducedQty = BigDecimal.ZERO;

	/**
	 * 履行数量
	 */
	private BigDecimal fulfillmentQty = BigDecimal.ZERO;

	/**
	 * 获取Redis记录的库存Key
	 * @param accDate 库存日期
	 * @return Redis记录的库存Key
	 */
	public String getRedisKey(String accDate) {
		return InventoryUtils.getStockRedisKey(accDate, getProductCode(), getWhCode(), getLocCode());
	}

}
