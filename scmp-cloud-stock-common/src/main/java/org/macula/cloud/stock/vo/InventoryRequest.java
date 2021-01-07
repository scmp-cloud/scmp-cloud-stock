package org.macula.cloud.stock.vo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.macula.cloud.stock.util.InventoryUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedMap.Builder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InventoryRequest extends StockRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 发货方式
	 */
	private String deliveryType;

	/**
	 * 商品明细，以productCode不得重复
	 */
	private Set<ProductQuantity> details;

	public static InventoryRequest of(String clientId, String channel, String document, String version) {
		InventoryRequest request = new InventoryRequest();
		request.setClientId(clientId);
		request.setChannel(channel);
		request.setDocument(document);
		request.setVersion(version);
		return request;
	}

	/**
	 * 获取明细的产品编号列表用于排序
	 */
	@JsonIgnore
	public SortedMap<String, BigDecimal> getSortedActuallyProductQuantities() {
		Builder<String, BigDecimal> builder = ImmutableSortedMap.<String, BigDecimal>naturalOrder();
		if (CollectionUtils.isNotEmpty(this.getDetails())) {
			if (StringUtils.isEmpty(getAccDate())) {
				throw new IllegalArgumentException("getAccDate() shouldn't be null");
			}
			this.getDetails().forEach(o -> {
				if (o.getActuallyQuantity() == null || o.getActuallyQuantity().compareTo(BigDecimal.ZERO) < 0) {
					throw new IllegalArgumentException("ActuallyQuantity should > 0");
				}
				builder.put(o.getActuallyStockRedisKey(getAccDate()), o.getApplyQuantity());
			});
		}
		return builder.build();
	}

	/**
	 * 获取明细的产品编号列表用于排序
	 */
	@JsonIgnore
	public SortedMap<String, BigDecimal> getSortedApplyProductQuantities() {
		Builder<String, BigDecimal> builder = ImmutableSortedMap.<String, BigDecimal>naturalOrder();
		if (CollectionUtils.isNotEmpty(this.getDetails())) {
			if (StringUtils.isEmpty(getAccDate())) {
				throw new IllegalArgumentException("getAccDate() shouldn't be null");
			}
			this.getDetails().forEach(o -> {
				if (o.getApplyQuantity() == null || o.getApplyQuantity().compareTo(BigDecimal.ZERO) < 0) {
					throw new IllegalArgumentException("ApplyQuantity should > 0");
				}
				builder.put(o.getApplyStockRedisKey(getAccDate()), o.getApplyQuantity());
			});
		}
		return builder.build();
	}

	public void updateActuallyQuantityResult(String productKey, final BigDecimal availableQty) {
		if (CollectionUtils.isNotEmpty(this.getDetails())) {
			this.getDetails().forEach(o -> {
				if (StringUtils.equals(productKey, o.getActuallyStockRedisKey(getAccDate()))) {
					o.setActuallyQuantity(availableQty);
				}
			});
		}
	}

	public void updateFulfillmentResult(String applyRedisKey, String fulfillmentRedisKey) {
		if (CollectionUtils.isNotEmpty(this.getDetails())) {
			this.getDetails().forEach(o -> {
				if (o.getApplyStockRedisKey(getAccDate()).equals(applyRedisKey)) {
					String[] parts = InventoryUtils.resolveStockRedisKey(fulfillmentRedisKey);
					o.setFulfillmentWhCode(parts[2]);
					o.setFulfillmentLocCode(parts[3]);
					o.setFulfillmentQuantity(o.getActuallyQuantity());
				}
			});
		}
	}

	/**
	 * 添加商品数量明细
	 */
	public void addProductQuantity(ProductQuantity... productQuantity) {
		addProductQuantity(Arrays.asList(productQuantity));
	}

	/**
	 * 添加商品数量明细
	 */
	public void addProductQuantity(Collection<ProductQuantity> productQuantities) {
		if (details == null) {
			details = new HashSet<>();
		}
		details.addAll(productQuantities);
	}
}
