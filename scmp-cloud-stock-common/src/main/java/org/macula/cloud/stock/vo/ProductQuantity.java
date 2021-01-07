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
public class ProductQuantity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 产品SKU
	 */
	private String productCode;

	/**
	 * 申请货仓代码(for gbss)
	 */
	private String applyWhCode;

	/**
	 * 申请仓库库区 
	 */
	private String applyLocCode;

	/**
	 * 申请产品数量
	 */
	private BigDecimal applyQuantity = BigDecimal.ZERO;

	/**
	 * 库存执行货仓代码(for reserve, commit, release)
	 */
	private String actuallyWhCode;

	/**
	 * 库存执行仓库库区
	 */
	private String actuallyLocCode;

	/**
	 * 库存实际执行产品数量
	 */
	private BigDecimal actuallyQuantity = BigDecimal.ZERO;

	/**
	 * 履行仓库库区(for fulfillment)
	 */
	private String fulfillmentWhCode;

	/**
	 * 履行执行仓库库区
	 */
	private String fulfillmentLocCode;

	/**
	 * 履行产品数量
	 */
	private BigDecimal fulfillmentQuantity = BigDecimal.ZERO;

	/**
	 * 库存状态
	 */
	private String status;

	public static ProductQuantity of(String productCode, String whCode, String locCode) {
		ProductQuantity pq = new ProductQuantity();
		pq.setProductCode(productCode);
		pq.setApplyWhCode(whCode);
		pq.setApplyLocCode(locCode);
		pq.setActuallyWhCode(whCode);
		pq.setActuallyLocCode(locCode);
		return pq;
	}

	public static ProductQuantity of(String productCode, String whCode, String locCode, BigDecimal quantity) {
		ProductQuantity pq = of(productCode, whCode, locCode);
		pq.setApplyQuantity(quantity);
		return pq;
	}

	public static ProductQuantity clone(ProductQuantity that) {
		ProductQuantity the = ProductQuantity.of(that.getProductCode(), that.getApplyWhCode(), that.getApplyWhCode(), that.getApplyQuantity());
		the.setActuallyWhCode(that.getActuallyWhCode());
		the.setActuallyLocCode(that.getActuallyLocCode());
		the.setActuallyQuantity(that.getActuallyQuantity());
		the.setFulfillmentWhCode(that.getFulfillmentWhCode());
		the.setFulfillmentLocCode(that.getFulfillmentLocCode());
		the.setFulfillmentQuantity(that.getFulfillmentQuantity());
		the.setStatus(that.getStatus());
		return the;
	}

	@Override
	public boolean equals(Object obj) {
		return productCode != null && obj != null && obj instanceof ProductQuantity && productCode.equals(((ProductQuantity) obj).getProductCode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((applyWhCode == null) ? 0 : applyWhCode.hashCode());
		result = prime * result + ((actuallyWhCode == null) ? 0 : actuallyWhCode.hashCode());
		result = prime * result + ((fulfillmentWhCode == null) ? 0 : fulfillmentWhCode.hashCode());
		return result;
	}

	/**
	 * 获取Redis申请的库存Key
	 * @param accDate 库存日期
	 * @return Redis记录的库存Key
	 */
	public String getApplyStockRedisKey(String accDate) {
		return InventoryUtils.getStockRedisKey(accDate, getProductCode(), getApplyWhCode(), getApplyLocCode());
	}

	/**
	 * 获取Redis执行的库存Key
	 * @param accDate 库存日期
	 * @return Redis记录的库存Key
	 */
	public String getActuallyStockRedisKey(String accDate) {
		return InventoryUtils.getStockRedisKey(accDate, getProductCode(), getActuallyWhCode(), getActuallyLocCode());
	}

	/**
	 * 获取Redis记录的Route Key
	 * @param accDate
	 * @return
	 */
	public String getRouteStockRedisKey(String accDate, String deliveryType) {
		return InventoryUtils.getRouteRedisKey(accDate, getProductCode(), getApplyWhCode(), deliveryType);
	}

}
