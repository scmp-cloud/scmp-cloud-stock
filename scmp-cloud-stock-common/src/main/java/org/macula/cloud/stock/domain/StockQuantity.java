package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CN_STOCK_QUANTITY")
public class StockQuantity extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "FIXED_QTY")
	private boolean fixedQty;

	@Column(name = "TOTAL_QTY")
	private BigDecimal totalQty;

}
