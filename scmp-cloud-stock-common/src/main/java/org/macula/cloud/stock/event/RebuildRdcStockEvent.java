package org.macula.cloud.stock.event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * RDC（实体仓库）重建事件。
 * 改事件的产生可能来自：
 * 1、用户管理修改了分配比例；
 * 2、用户做了虚增操作；
 * 3、WMS或SAP做了库存修改操作；
 * 该改事件产生后，需要先放入MQ入队，然后进行接收和处理。
 */
public class RebuildRdcStockEvent extends InventoryEvent {

	private static final long serialVersionUID = 1L;

	private Set<String> whCodes = new HashSet<String>();

	public RebuildRdcStockEvent() {

	}

	public RebuildRdcStockEvent(String... whCode) {
		super();
		addRdc(whCode);
	}

	public RebuildRdcStockEvent(InventoryEvent parentEvent, String... whCode) {
		super(parentEvent);
		addRdc(whCode);
	}

	public void addRdc(String... whCode) {
		if (whCode.length > 0) {
			whCodes.addAll(Arrays.asList(whCode));
		}
	}
}
