package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CN_STOCK_STATUS")
public class StockStatus extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ACC_DATE", nullable = false)
	private String accDate;

	@Column(name = "PRODUCT_CODE", nullable = false)
	private String productCode;

	@Column(name = "WH_CODE", nullable = false)
	private String whCode;

	@Column(name = "LOC_CODE", nullable = false)
	private String locCode;

	@Column(name = "INITIALIZED_QTY", nullable = false)
	private BigDecimal initializedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_INITIALIZED_TIME")
	private Date lastInitializeTime;

	@Column(name = "BALANCE_QTY", nullable = false)
	private BigDecimal balanceQty;

	@Column(name = "INCREASED_QTY", nullable = false)
	private BigDecimal increasedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_INCREASED_TIME")
	private Date lastIncreasedTime;

	@Column(name = "REDUCED_QTY", nullable = false)
	private BigDecimal reducedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_REDUCED_TIME")
	private Date lastReducedTime;

	@Column(name = "RESERVED_QTY", nullable = false)
	private BigDecimal reservedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_RESERVED_TIME")
	private Date lastReservedTime;

	@Column(name = "RELEASED_QTY", nullable = false)
	private BigDecimal releasedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_RELEASED_TIME")
	private Date lastReleasedTime;

	@Column(name = "COMMITED_QTY", nullable = false)
	private BigDecimal commitedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_COMMITED_TIME")
	private Date lastCommitedTime;

	@Column(name = "FULFILLMENT_QTY", nullable = false)
	private BigDecimal fulfillmentQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_FULFILLMENT_TIME")
	private Date lastFulfillmentTime;

	@Column(name = "COMMENTS")
	private String comments;

}
