package org.macula.cloud.stock.vo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedMap.Builder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BalanceRequest extends StockRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品明细，以productCode不得重复
	 */
	private Set<BalanceQuantity> details;

	public static BalanceRequest of(String clientId, String channel, String document, String version) {
		BalanceRequest request = new BalanceRequest();
		request.setClientId(clientId);
		request.setChannel(channel);
		request.setDocument(document);
		request.setVersion(version);
		return request;
	}

	/**
	 * 获取库存排序
	 */
	@JsonIgnore
	public SortedMap<String, BalanceQuantity> getSortedBalanceQuantities() {
		Builder<String, BalanceQuantity> builder = ImmutableSortedMap.<String, BalanceQuantity>naturalOrder();
		if (CollectionUtils.isNotEmpty(this.getDetails())) {
			if (StringUtils.isEmpty(getAccDate())) {
				throw new IllegalArgumentException("accDate shouldn't be null");
			}
			this.getDetails().forEach(o -> {
				builder.put(o.getRedisKey(getAccDate()), o);
			});
		}
		return builder.build();
	}

	/**
	 * 更新库存数据
	 */
	public void updateBalanceResult(String productKey, final BigDecimal quantity) {
		if (CollectionUtils.isNotEmpty(this.getDetails())) {
			for (BalanceQuantity o : details) {
				if (StringUtils.startsWith(productKey, o.getRedisKey(getAccDate()))) {
					if (StringUtils.endsWith(productKey, "-initializedQty")) {
						o.setInitializedQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-balanceQty")) {
						o.setBalanceQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-reservedQty")) {
						o.setReservedQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-commitedQty")) {
						o.setCommitedQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-releasedQty")) {
						o.setReleasedQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-increasedQty")) {
						o.setIncreasedQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-reducedQty")) {
						o.setReducedQty(quantity);
					} else if (StringUtils.endsWith(productKey, "-fulfillmentQty")) {
						o.setReducedQty(quantity);
					}
					break;
				}
			}
		}
	}

	public void updateFulfillmentResult(String key, String value) {

	}

	/**
	 * 添加库存请求明细
	 * @param inventoryBalance
	 */
	public void addInventoryBalance(BalanceQuantity... inventoryBalance) {
		if (details == null) {
			details = new HashSet<>();
		}
		details.addAll(Arrays.asList(inventoryBalance));
	}

}
