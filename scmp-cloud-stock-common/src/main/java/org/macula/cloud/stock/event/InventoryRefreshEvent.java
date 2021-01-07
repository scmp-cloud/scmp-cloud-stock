package org.macula.cloud.stock.event;

import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单库刷新事件
 */
@Getter
@Setter
public class InventoryRefreshEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private BalanceRequest request;

	private BalanceResponse response;
}
