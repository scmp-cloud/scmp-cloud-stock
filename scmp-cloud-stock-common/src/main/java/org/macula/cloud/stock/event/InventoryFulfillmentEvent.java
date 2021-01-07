package org.macula.cloud.stock.event;

import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单库存履行事件，该事件代表履行成功。
 */
@Getter
@Setter
public class InventoryFulfillmentEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private InventoryRequest request;

	private InventoryResponse response;
}
