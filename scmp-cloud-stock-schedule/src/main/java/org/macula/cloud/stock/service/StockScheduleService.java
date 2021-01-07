package org.macula.cloud.stock.service;

public interface StockScheduleService {

	/**
	 * 1. 同步每日实物可用库存
	 * @param accDate 
	 */
	int loadRdcStock(String accDate);

	/**
	 * 更新库存路径
	 * @param accDate
	 */
	void updateGroupPath(String accDate);

	/**
	 * 载入Group path 到Redis
	 * @param accDate
	 */
	void loadGroup2Redis(String accDate);

	/**
	 * 删除Group path的Redis数据
	 * @param accDate
	 */
	void unloadGroup2Redis(String accDate);

	/** 
	 * 2. 加载特定SKU设定的StockGroup库存
	 * @param accDate 
	 */
	int updateProductGroupStock(String accDate);

	/**
	 * 3. 计算StockGroup默认设定库存
	 * @param accDate 
	 */
	int updateDefaultGroupStock(String accDate);

	/**
	 * 4. 加载未上传SAP订单库存
	 * @param accDate 
	 */
	void loadUntriggerOrderStock(String accDate);

	/**
	 * 5. 加载预留未提交订单库存
	 * @param accDate 
	 */
	void loadReservedOrderStock(String accDate);

	/**
	 * 载入库存到Redis中
	 * @param accDate
	 */
	void loadStock2Redis(String accDate);

	/**
	 * 删除StockRoute在Redis中的信息
	 * @param accDate
	 */
	void unloadRoute2Redis(String accDate);

	/**
	 * 载入库存路由到Redis
	 * @param accDate
	 */
	void loadRoute2Redis(String accDate);

	/**
	 * 删除当天库存信息
	 * @param accDate
	 */
	int deleteStockStatus(String accDate);

	/**
	 * 删除当天StockGroup计算出的库存信息
	 * @param accDate
	 */
	int deleteGroupStock(String accDate);

	/**
	 * 删除Redis中的库存信息
	 * @param accDate
	 */
	void unloadStock2Redis(String accDate);
}
