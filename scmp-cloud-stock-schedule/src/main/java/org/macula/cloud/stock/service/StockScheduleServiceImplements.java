package org.macula.cloud.stock.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.macula.cloud.stock.domain.StockGroup;
import org.macula.cloud.stock.domain.StockRoute;
import org.macula.cloud.stock.feign.StockFeignClient;
import org.macula.cloud.stock.flink.StockStatusRedisLoadingSink;
import org.macula.cloud.stock.flink.StockStatusRedisLoadingSource;
import org.macula.cloud.stock.repository.StockGroupRepository;
import org.macula.cloud.stock.repository.StockRouteRepository;
import org.macula.cloud.stock.repository.StockStatusRepository;
import org.macula.cloud.stock.util.InventoryUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StockScheduleServiceImplements implements StockScheduleService {

	private StockStatusRepository stockStatusRepository;
	private StockGroupRepository stockGroupRepository;
	private StockRouteRepository stockRouteRepository;
	private StringRedisTemplate stringRedisTemplate;
	private StockFeignClient stockClient;

	@PostConstruct
	public void initialFlinkBeans() {
		StockStatusRedisLoadingSink.initializeBeans(stockStatusRepository, stockClient);
		StockStatusRedisLoadingSource.initializeBeans(stockStatusRepository);
	}

	@Transactional
	@Override
	public int deleteStockStatus(String accDate) {
		return stockStatusRepository.deleteStockStatus(accDate);
	}

	@Transactional
	@Override
	public int deleteGroupStock(String accDate) {
		return stockStatusRepository.deleteGroupStock(accDate);
	}

	@Override
	public void unloadStock2Redis(String accDate) {
		stringRedisTemplate.delete(stringRedisTemplate.keys(accDate + ":"));
	}

	@Transactional
	@Override
	public int loadRdcStock(String accDate) {
		return 0;
	}

	@Transactional
	@Override
	public int updateProductGroupStock(String accDate) {
		Long maxLevel = stockGroupRepository.findProductGroupMaxLevel();
		int count = 0;
		for (int level = 1; level <= maxLevel; level++) {
			count += stockStatusRepository.updateProductGroupStockByLevel(accDate, level);
		}
		return count;
	}

	@Transactional
	@Override
	public int updateDefaultGroupStock(String accDate) {
		Long maxLevel = stockGroupRepository.findDefaultGroupMaxLevel();
		int count = 0;
		for (int level = 1; level <= maxLevel; level++) {
			count += stockStatusRepository.updateDefaultGroupStockByLevel(accDate, level);
		}
		return count;
	}

	@Transactional
	@Override
	public void loadUntriggerOrderStock(String accDate) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public void loadReservedOrderStock(String accDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadStock2Redis(String accDate) {
		log.info("Start read stock to Redis ...");

		StockStatusRedisLoadingSource source = new StockStatusRedisLoadingSource(accDate);
		StockStatusRedisLoadingSink sink = new StockStatusRedisLoadingSink(accDate);

		StreamExecutionEnvironment execution = StreamExecutionEnvironment.getExecutionEnvironment();
		execution.setMaxParallelism(8);
		execution.enableCheckpointing(60000, CheckpointingMode.EXACTLY_ONCE);
		DataStream<PageRequest> dataStream = execution.addSource(source, TypeInformation.of(PageRequest.class));
		dataStream.addSink(sink);
		try {
			execution.execute("loadStock2Redis");
		} catch (Exception e) {
			log.error("Schedule read stock to Redis error, ", e);
		}
		log.info("Schedule read stock to Redis ...");
	}

	@Override
	public void unloadRoute2Redis(String accDate) {
		stringRedisTemplate.delete(stringRedisTemplate.keys(InventoryUtils.getRouteKeysPattern(accDate)));
	}

	@Override
	public void loadRoute2Redis(String accDate) {
		List<StockRoute> routes = stockRouteRepository.findCurrentRoutes(accDate);
		for (StockRoute stockRoute : routes) {
			String routeKey = stockRoute.getRouteRedisKey(accDate);
			stringRedisTemplate.opsForHash().put(routeKey, "stockCode", stockRoute.getStockCode());
			stringRedisTemplate.opsForHash().put(routeKey, "fulfillmentPath", InventoryUtils.createValuePath(stockRoute.getFulfillmentPath()));
		}
	}

	@Override
	@Transactional
	public void updateGroupPath(String accDate) {
		List<StockGroup> groups = stockGroupRepository.findCurrentGroups(accDate);
		Set<StockGroup> caculatedGroups = new HashSet<StockGroup>();
		for (StockGroup stockGroup : groups) {
			caculateGroupPath(stockGroup, groups, caculatedGroups);
		}
		stockGroupRepository.saveAll(groups);
	}

	private void caculateGroupPath(StockGroup stockGroup, List<StockGroup> groups, Set<StockGroup> caculatedGroups) {
		if (caculatedGroups.contains(stockGroup)) {
			return;
		}
		StockGroup parentGroup = null;
		for (StockGroup p : groups) {
			if (p.isParentGroupOf(stockGroup)) {
				parentGroup = p;
			}
		}
		if (parentGroup == null) {
			stockGroup.setFullPath(InventoryUtils.createValuePath(stockGroup.getWhCode(), stockGroup.getParentCode()));
		} else {
			if (!caculatedGroups.contains(parentGroup)) {
				caculateGroupPath(parentGroup, groups, caculatedGroups);
			}
			stockGroup.setFullPath(InventoryUtils.createValuePath(stockGroup.getWhCode(), parentGroup.getFullPath()));
		}
		caculatedGroups.add(stockGroup);
	}

	@Override
	public void unloadGroup2Redis(String accDate) {
		stringRedisTemplate.delete(stringRedisTemplate.keys(InventoryUtils.getGroupKeysPattern(accDate)));
	}

	@Override
	public void loadGroup2Redis(String accDate) {
		List<StockGroup> groups = stockGroupRepository.findCurrentGroups(accDate);
		for (StockGroup stockGroup : groups) {
			stringRedisTemplate.opsForValue().set(stockGroup.getGroupRedisKey(accDate), stockGroup.getFullPath());
		}
	}

}
