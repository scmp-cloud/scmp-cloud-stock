package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisStockStatus extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String redisKey;

	private BigDecimal initializedQty;

	private BigDecimal balanceQty;

	private BigDecimal reservedQty;

	private BigDecimal releasedQty;

	private BigDecimal commitedQty;

	private BigDecimal increasedQty;

	private BigDecimal reducedQty;

	private BigDecimal fulfillmentQty;
}
