package org.macula.cloud.stock.event;

import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单库增加事件
 */
@Getter
@Setter
public class InventoryIncreaseEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private InventoryRequest request;

	private InventoryResponse response;
}
