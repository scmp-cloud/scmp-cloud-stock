package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CN_STOCK_REQ_DETAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class StockRequestDetail extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	/** 申请仓码 */
	@Column(name = "APPLY_WH_CODE")
	private String applyWhCode;

	/** 申请库区 */
	@Column(name = "APPLY_LOC_CODE")
	private String applyLocCode;

	/** 库存发生仓码 */
	@Column(name = "ACTUAL_WH_CODE")
	private String actuallyWhCode;

	/** 库存发生库区 */
	@Column(name = "ACTUAL_LOC_CODE")
	private String actuallyLocCode;

	/** 实物履行仓码 */
	@Column(name = "FULFILL_WH_CODE")
	private String fulfillmentWhCode;

	/** 实物履行库区 */
	@Column(name = "FULFILL_LOC_CODE")
	private String fulfillmentLocCode;

	@Column(name = "APPLY_QTY")
	private BigDecimal applyQty;

	@Column(name = "INITIALIZED_QTY")
	private BigDecimal initializedQty;

	@Column(name = "BALANCE_QTY")
	private BigDecimal balanceQty;

	@Column(name = "RESERVED_QTY")
	private BigDecimal reservedQty;

	@Column(name = "RELEASED_QTY")
	private BigDecimal releasedQty;

	@Column(name = "COMMITED_QTY")
	private BigDecimal commitedQty;

	@Column(name = "INCREASED_QTY")
	private BigDecimal increasedQty;

	@Column(name = "REDUCED_QTY")
	private BigDecimal reducedQty;

	@Column(name = "FULFILLMENT_QTY")
	private BigDecimal fulfillmentQty;

}
