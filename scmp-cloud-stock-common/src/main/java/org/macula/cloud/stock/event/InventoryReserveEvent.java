package org.macula.cloud.stock.event;

import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单库存锁定事件。
 * 该事件会在锁定成功后产生。
 */
@Getter
@Setter
public class InventoryReserveEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private InventoryRequest request;

	private InventoryResponse response;

}
