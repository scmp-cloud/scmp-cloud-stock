package org.macula.cloud.stock.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.macula.cloud.stock.event.InventoryEvent;
import org.macula.cloud.stock.exception.StockException;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.ProductQuantity;
import org.macula.cloud.stock.vo.StockRequest;

import cn.hutool.core.date.DateUtil;

public class InventoryUtils {

	private static ThreadLocal<InventoryEvent> threadLocal = new ThreadLocal<InventoryEvent>();

	public static String getUniqueIndexKey(StockRequest request) {
		return getUniqueIndexKey(request.getClientId(), request.getChannel(), request.getDocument(), request.getVersion());
	}

	public static String getUniqueIndexKey(String clientId, String channel, String document, String version) {
		return String.format("%s:%s:%s:%s", clientId, channel, document, version);
	}

	public static String getStockRedisKey(Date accDate, String productCode, String whCode, String locCode) {
		return getStockRedisKey(DateUtil.format(accDate, "yyyyMMdd"), productCode, whCode, locCode);
	}

	public static String getStockRedisKey(String accDate, String productCode, String whCode, String locCode) {
		return String.format("%s:%s:%s:%s", accDate, productCode, whCode, locCode);
	}

	public static String[] resolveStockRedisKey(String redisStockKey) {
		return redisStockKey.split(":");
	}

	public static String getRouteRedisKey(String accDate, String productCode, String whCode, String deliveryType) {
		return String.format("%s:%s:%s:%s:%s", "ROUTES", accDate, productCode, whCode, deliveryType);
	}

	public static String getRouteKeysPattern(String accDate) {
		return String.format("%s:%s:*", "ROUTES", accDate);
	}

	public static String getFulfillmentRedisKey(String accDate, String productCode, String applyWhCode) {
		return String.format("%s:%s:%s:%s:%s", "FULFILLMENTS", accDate, productCode, applyWhCode);
	}

	public static String getFulfillmentKeysPattern(String accDate) {
		return String.format("%s:%s:*", "FULFILLMENTS", accDate);
	}

	public static String getGroupKeysPattern(String accDate) {
		return String.format("%s:%s:*", "GROUPS", accDate);
	}

	public static String getGroupRedisKey(String accDate, String productCode, String whCode) {
		return String.format("%s:%s:%s:%s", "GROUPS", accDate, productCode, whCode);
	}

	public static String createValuePath(List<String> paths) {
		return StringUtils.join(paths, ",");
	}

	public static String createValuePath(String... path) {
		return createValuePath(Arrays.asList(path));
	}

	public static List<String> resolveValuePath(String path) {
		if (StringUtils.isBlank(path)) {
			return new ArrayList<String>();
		}
		return Arrays.asList(path.split(","));
	}

	public static InventoryRequest resolveRequestKey(String redisKey) {
		String[] arrays = redisKey.split(":");
		return InventoryRequest.of(arrays[1], arrays[2], arrays[3], arrays[4]);
	}

	public static ProductQuantity resolveQtyKey(String redisKey) {
		String[] arrays = redisKey.split(":");
		return ProductQuantity.of(arrays[1], arrays[2], arrays[3]);
	}

	public static boolean hasCallSuccess(BigDecimal status) {
		return 1 == status.intValue();
	}

	public static String getStatusMessage(BigDecimal status) {
		int value = status.intValue();
		switch (value) {
		case 1:
			return "正常";
		case 0:
			return "预留调用已存在唯一键值";
		case -1:
			return "确认或取消调用不存在已预留的键值";
		case -2:
			return "库存预留/扣减/履行数量不足确认数量";
		}
		throw new StockException("不存在的返回状态值：" + status);
	}

	public static InventoryEvent getCurrentEvent() {
		return threadLocal.get();
	}

	public static void setCurrentEvent(InventoryEvent event) {
		threadLocal.set(event);
	}

}
