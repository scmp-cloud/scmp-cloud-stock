package org.macula.cloud.stock.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.macula.cloud.stock.event.InventoryCommitEvent;
import org.macula.cloud.stock.event.InventoryEvent;
import org.macula.cloud.stock.event.InventoryFulfillmentEvent;
import org.macula.cloud.stock.event.InventoryReduceEvent;
import org.macula.cloud.stock.event.InventoryRefreshEvent;
import org.macula.cloud.stock.event.InventoryReleaseEvent;
import org.macula.cloud.stock.event.InventoryReserveEvent;
import org.macula.cloud.stock.exception.StockException;
import org.macula.cloud.stock.service.InventoryCenterService;
import org.macula.cloud.stock.util.InventoryUtils;
import org.macula.cloud.stock.vo.BalanceQuantity;
import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;
import org.macula.cloud.stock.vo.ProductQuantity;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryCenterServiceRedisImpl implements InventoryCenterService {

	private static final String BindingName = "stock-out-0";

	private StringRedisTemplate stringRedisTemplate;
	private DefaultRedisScript<String> stockReserveScript;
	private DefaultRedisScript<String> stockCommitScript;
	private DefaultRedisScript<String> stockReleaseScript;
	private DefaultRedisScript<String> stockFulfillmentScript;
	private DefaultRedisScript<String> stockIncreaseScript;
	private DefaultRedisScript<String> stockReduceScript;
	private DefaultRedisScript<String> stockRefreshScript;
	private DefaultRedisScript<String> stockStatusScript;
	private StreamBridge streamBridge;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String STATUS = "status";

	@Override
	public BalanceResponse status(BalanceRequest request) {
		BalanceResponse response = null;
		List<String> keys = request.getDetails().stream().map(t -> t.getRedisKey(request.getAccDate())).collect(Collectors.toList());
		keys.add(InventoryUtils.getUniqueIndexKey(request));
		String result = stringRedisTemplate.execute(stockStatusScript, keys);
		Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
		BigDecimal status = executeResult.remove(STATUS);
		for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
			request.updateBalanceResult(entry.getKey(), entry.getValue());
		}
		boolean validated = InventoryUtils.hasCallSuccess(status);
		response = validated ? BalanceResponse.success(request)
				: BalanceResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		return response;
	}

	@Override
	public InventoryResponse reserve(InventoryRequest request) {
		updateRouteStockCode(request);
		InventoryResponse response = null;
		InventoryReserveEvent event = new InventoryReserveEvent();
		event.setRequest(request);
		try {
			SortedMap<String, BigDecimal> productMaps = request.getSortedActuallyProductQuantities();
			List<String> keys = new ArrayList<String>(productMaps.keySet());
			keys.add(InventoryUtils.getUniqueIndexKey(request));
			String result = stringRedisTemplate.execute(stockReserveScript, keys,
					productMaps.values().stream().map(BigDecimal::toString).collect(Collectors.toList()).toArray());
			Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
			BigDecimal status = executeResult.remove(STATUS);
			for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
				request.updateActuallyQuantityResult(entry.getKey(), entry.getValue());
			}
			boolean validated = InventoryUtils.hasCallSuccess(status);
			response = validated ? InventoryResponse.success(request)
					: InventoryResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	@Override
	public InventoryResponse commit(InventoryRequest request) {
		updateRouteStockCode(request);
		InventoryResponse response = null;
		InventoryCommitEvent event = new InventoryCommitEvent();
		event.setRequest(request);
		try {
			SortedMap<String, BigDecimal> productMaps = request.getSortedActuallyProductQuantities();
			List<String> keys = new ArrayList<String>(productMaps.keySet());
			keys.add(InventoryUtils.getUniqueIndexKey(request));
			String result = stringRedisTemplate.execute(stockCommitScript, keys,
					productMaps.values().stream().map(BigDecimal::toString).collect(Collectors.toList()).toArray());
			Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
			BigDecimal status = executeResult.remove(STATUS);
			for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
				request.updateActuallyQuantityResult(entry.getKey(), entry.getValue());
			}
			boolean validated = InventoryUtils.hasCallSuccess(status);
			response = validated ? InventoryResponse.success(request)
					: InventoryResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	@Override
	public InventoryResponse release(InventoryRequest request) {
		updateRouteStockCode(request);
		InventoryResponse response = null;
		InventoryReleaseEvent event = new InventoryReleaseEvent();
		event.setRequest(request);
		try {
			SortedMap<String, BigDecimal> productMaps = request.getSortedActuallyProductQuantities();
			List<String> keys = new ArrayList<String>(productMaps.keySet());
			keys.add(InventoryUtils.getUniqueIndexKey(request));
			String result = stringRedisTemplate.execute(stockReleaseScript, keys,
					productMaps.values().stream().map(BigDecimal::toString).collect(Collectors.toList()).toArray());
			Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
			BigDecimal status = executeResult.remove("status");
			for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
				request.updateActuallyQuantityResult(entry.getKey(), entry.getValue());
			}
			boolean validated = InventoryUtils.hasCallSuccess(status);
			response = validated ? InventoryResponse.success(request)
					: InventoryResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	@Override
	public InventoryResponse fulfillment(InventoryRequest request) {
		updateFulfillmentStockCode(request);
		InventoryResponse response = null;
		InventoryFulfillmentEvent event = new InventoryFulfillmentEvent();
		event.setRequest(request);
		try {
			List<String> keys = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			Set<ProductQuantity> details = request.getDetails();
			for (ProductQuantity d : details) {
				values.add(d.getApplyStockRedisKey(request.getAccDate()));
				values.add(d.getActuallyQuantity().toString());
				List<String> fulfillmentCodes = InventoryUtils.resolveValuePath(d.getFulfillmentWhCode());
				for (String fulfillmentCode : fulfillmentCodes) {
					values.add(InventoryUtils.getStockRedisKey(request.getAccDate(), d.getProductCode(), fulfillmentCode, d.getFulfillmentLocCode()));
				}
				keys.add(String.valueOf(fulfillmentCodes.size() + 2));
			}
			keys.add(InventoryUtils.getUniqueIndexKey(request));

			String result = stringRedisTemplate.execute(stockFulfillmentScript, keys, values.toArray());
			Map<String, String> executeResult = getFulfillmentRedisResult(result);
			BigDecimal status = new BigDecimal(executeResult.remove(STATUS));
			boolean validated = InventoryUtils.hasCallSuccess(status);
			if (validated) {
				for (Map.Entry<String, String> entry : executeResult.entrySet()) {
					request.updateFulfillmentResult(entry.getKey(), entry.getValue());
				}
			}
			response = validated ? InventoryResponse.success(request)
					: InventoryResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	@Override
	public InventoryResponse increase(InventoryRequest request) {
		InventoryResponse response = null;
		InventoryReduceEvent event = new InventoryReduceEvent();
		event.setRequest(request);
		try {
			expandRequestByGroup(request);
			SortedMap<String, BigDecimal> productMaps = request.getSortedApplyProductQuantities();
			List<String> keys = new ArrayList<String>(productMaps.keySet());
			keys.add(InventoryUtils.getUniqueIndexKey(request));
			String result = stringRedisTemplate.execute(stockIncreaseScript, keys,
					productMaps.values().stream().map(BigDecimal::toString).collect(Collectors.toList()).toArray());
			Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
			BigDecimal status = executeResult.remove(STATUS);
			for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
				request.updateActuallyQuantityResult(entry.getKey(), entry.getValue());
			}
			boolean validated = InventoryUtils.hasCallSuccess(status);
			response = validated ? InventoryResponse.success(request)
					: InventoryResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	@Override
	public InventoryResponse reduce(InventoryRequest request) {
		InventoryResponse response = null;
		InventoryReduceEvent event = new InventoryReduceEvent();
		event.setRequest(request);
		try {
			expandRequestByGroup(request);
			SortedMap<String, BigDecimal> productMaps = request.getSortedApplyProductQuantities();
			List<String> keys = new ArrayList<String>(productMaps.keySet());
			keys.add(InventoryUtils.getUniqueIndexKey(request));
			String result = stringRedisTemplate.execute(stockReduceScript, keys,
					productMaps.values().stream().map(BigDecimal::toString).collect(Collectors.toList()).toArray());
			Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
			BigDecimal status = executeResult.remove(STATUS);
			for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
				request.updateActuallyQuantityResult(entry.getKey(), entry.getValue());
			}
			boolean validated = InventoryUtils.hasCallSuccess(status);
			response = validated ? InventoryResponse.success(request)
					: InventoryResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	/**
	 * 按StockGroup的配置扩展受影响的stock
	 */
	private void expandRequestByGroup(InventoryRequest request) {
		Set<ProductQuantity> details = request.getDetails();
		Set<ProductQuantity> expends = new HashSet<ProductQuantity>();
		for (ProductQuantity pq : details) {
			String groupRedisKey = InventoryUtils.getGroupRedisKey(request.getAccDate(), pq.getProductCode(), pq.getApplyWhCode());
			List<String> codes = null;
			if (stringRedisTemplate.hasKey(groupRedisKey)) {
				codes = InventoryUtils.resolveValuePath(stringRedisTemplate.opsForValue().get(groupRedisKey));
			} else {
				groupRedisKey = InventoryUtils.getGroupRedisKey(request.getAccDate(), "*", pq.getApplyWhCode());
				if (stringRedisTemplate.hasKey(groupRedisKey)) {
					codes = InventoryUtils.resolveValuePath(stringRedisTemplate.opsForValue().get(groupRedisKey));
				}
			}
			if (CollectionUtils.isNotEmpty(codes)) {
				for (String code : codes) {
					if (!code.equals(pq.getApplyWhCode())) {
						ProductQuantity expend = ProductQuantity.clone(pq);
						expend.setApplyWhCode(code);
						expends.add(expend);
					}
				}
			}
		}
		request.addProductQuantity(expends);
	}

	@Override
	public BalanceResponse refresh(BalanceRequest request) {
		BalanceResponse response = null;
		InventoryRefreshEvent event = new InventoryRefreshEvent();
		event.setRequest(request);
		try {
			SortedMap<String, BalanceQuantity> productMaps = request.getSortedBalanceQuantities();
			List<String> keys = new ArrayList<String>(productMaps.keySet());
			keys.add(InventoryUtils.getUniqueIndexKey(request));
			List<String> quantities = new ArrayList<>();
			for (BalanceQuantity balance : productMaps.values()) {
				quantities.add(String.valueOf(balance.getInitializedQty()));
				quantities.add(String.valueOf(balance.getBalanceQty()));
				quantities.add(String.valueOf(balance.getReservedQty()));
				quantities.add(String.valueOf(balance.getReleasedQty()));
				quantities.add(String.valueOf(balance.getCommitedQty()));
				quantities.add(String.valueOf(balance.getIncreasedQty()));
				quantities.add(String.valueOf(balance.getReducedQty()));
				quantities.add(String.valueOf(balance.getFulfillmentQty()));
			}
			String result = stringRedisTemplate.execute(stockRefreshScript, keys, quantities.toArray());
			Map<String, BigDecimal> executeResult = getQtyRedisResult(result);
			BigDecimal status = executeResult.remove(STATUS);
			for (Map.Entry<String, BigDecimal> entry : executeResult.entrySet()) {
				request.updateBalanceResult(entry.getKey(), entry.getValue());
			}
			boolean validated = InventoryUtils.hasCallSuccess(status);
			response = validated ? BalanceResponse.success(request)
					: BalanceResponse.failed(String.valueOf(status.intValue()), InventoryUtils.getStatusMessage(status), request);
		} finally {
			event.setResponse(response);
			event.markPublished();
			sendEventAsMessage(event);
		}
		return response;
	}

	private Map<String, BigDecimal> getQtyRedisResult(String result) {
		try {
			return objectMapper.readValue(result, new TypeReference<Map<String, BigDecimal>>() {
			});
		} catch (JsonProcessingException e) {
			throw new StockException(String.format("GetQtyRedisResult %s error ", result), e);
		}
	}

	private Map<String, String> getFulfillmentRedisResult(String result) {
		try {
			return objectMapper.readValue(result, new TypeReference<Map<String, String>>() {
			});
		} catch (JsonProcessingException e) {
			throw new StockException(String.format("GetFulfillmentRedisResult %s error ", result), e);
		}
	}

	@Override
	public boolean deleteRefreshRequest(String idx) {
		return stringRedisTemplate.delete("REFRESH:" + idx);
	}

	@Override
	public boolean deleteIncreaseRequest(String idx) {
		return stringRedisTemplate.delete("INCREASED:" + idx);
	}

	@Override
	public boolean deleteReduceRequest(String idx) {
		return stringRedisTemplate.delete("REDUCED:" + idx);
	}

	@Override
	public boolean deleteReleaseRequest(String idx) {
		return stringRedisTemplate.delete("INCREASED:" + idx);
	}

	@Override
	public boolean deleteCommitRequest(String idx) {
		return stringRedisTemplate.delete("COMMITED:" + idx);
	}

	@Override
	public boolean dayEnd(String yesterday, String today) {
		Set<String> keys = stringRedisTemplate.keys("RESERVED:");
		for (String key : keys) {
			boolean candicate = false;
			InventoryRequest request = InventoryUtils.resolveRequestKey(key);
			request.setAccDate(yesterday);
			Map<Object, Object> entries = stringRedisTemplate.boundHashOps(key).entries();
			for (Map.Entry<Object, Object> entry : entries.entrySet()) {
				String entryKey = entry.getKey().toString();
				BigDecimal entryValue = new BigDecimal(entry.getValue().toString());
				if (StringUtils.startsWith(entryKey, yesterday)) {
					ProductQuantity pq = InventoryUtils.resolveQtyKey(entryKey);
					pq.setApplyQuantity(entryValue);
					request.addProductQuantity(pq);
					candicate = true;
				}
			}
			if (candicate) {
				this.release(request);
				request.setAccDate(today);
				this.reserve(request);
			}
		}
		return true;
	}

	@Override
	public InventoryResponse updateRouteStockCode(InventoryRequest request) {
		String accDate = request.getAccDate();
		String deliveryType = request.getDeliveryType();
		request.getDetails().forEach(d -> {
			if (d.getActuallyWhCode() == null) {
				d.setActuallyWhCode(d.getApplyWhCode());
			}
			if (d.getActuallyLocCode() == null) {
				d.setActuallyLocCode(d.getApplyLocCode());
			}
			String routeKey = d.getRouteStockRedisKey(accDate, deliveryType);
			if (stringRedisTemplate.hasKey(routeKey)) {
				Object stockCode = stringRedisTemplate.opsForHash().get(routeKey, "stockCode");
				if (stockCode != null) {
					d.setActuallyWhCode(String.valueOf(stockCode));
				}
			}
		});
		return InventoryResponse.success(request);
	}

	@Override
	public InventoryResponse updateFulfillmentStockCode(InventoryRequest request) {
		String accDate = request.getAccDate();
		String deliveryType = request.getDeliveryType();
		request.getDetails().forEach(d -> {
			if (d.getFulfillmentWhCode() == null) {
				d.setFulfillmentWhCode(d.getActuallyWhCode());
			}
			if (d.getFulfillmentLocCode() == null) {
				d.setFulfillmentLocCode(d.getActuallyLocCode());
			}
			String routeKey = d.getRouteStockRedisKey(accDate, deliveryType);
			if (stringRedisTemplate.hasKey(routeKey)) {
				String fulfillmentPath = (String) stringRedisTemplate.opsForHash().get(routeKey, "fulfillmentPath");
				if (StringUtils.isNotBlank(fulfillmentPath)) {
					d.setFulfillmentWhCode(fulfillmentPath);
				}
			}
		});
		return InventoryResponse.success(request);
	}

	/**
	 * 构建MQ消息并发送
	 * @param <T>
	 * @param event
	 * @return
	 */
	protected void sendEventAsMessage(InventoryEvent event) {
		Message<InventoryEvent> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId())
				.setHeader(RocketMQHeaders.TAGS, event.getClazz()).build();
		streamBridge.send(BindingName, message);
	}

}
