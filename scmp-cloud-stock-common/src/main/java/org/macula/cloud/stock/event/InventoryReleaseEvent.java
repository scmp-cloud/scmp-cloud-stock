package org.macula.cloud.stock.event;

import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单库存释放事件，该事件代表释放成功。
 * 订单库存释放的前提是：有与之匹配的锁定数据。
 */
@Getter
@Setter
public class InventoryReleaseEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private InventoryRequest request;

	private InventoryResponse response;
}
