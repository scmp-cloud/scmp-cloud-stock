package org.macula.cloud.stock.service;

import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.BalanceResponse;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.InventoryResponse;

public interface InventoryCenterService {

	/**
	 * 获取库存状态
	 */
	BalanceResponse status(BalanceRequest request);

	/**
	 * 请求库存，请求成功将会锁定对应的请求明细。步骤如下：
	 * 1、使用document+version判断是否有调用记录，如有则为重复调用，返回错误；（考虑将document+version+status放入Redis加速）
	 * 2、根据请求获取销售片区；
	 * 3、请求ProductCode排序，执行批量锁定；
	 * 4、判断所有请求库存是否满足，不满足返回错误；
	 * 5、如满足库存要求，执行锁定；
	 * 6、抛出库存锁定事件（扔给MQ去处理）
	 *
	 * @param inventoryRequest 库存锁定请求信息
	 * @return 库存锁定结果
	 */
	InventoryResponse reserve(InventoryRequest request);

	/**
	 * 确认锁定库存，如请求中没有明细，则表示确认全部。步骤如下：
	 * 1、使用document+version判断当前状态，只有已预留状态才能操作；（考虑将document+version+status放入Redis加速）
	 * 2、如无预留状态，返回错误；
	 * 3、如找到预留状态，将库存锁定转为实际消耗；
	 * 4、更新库存数据；
	 * 5、抛出库存确认事件（扔给MQ去处理）
	 *
	 * @param inventoryRequest 库存确认请求信息，如请求信息中没有明细，则表示确认全部。
	 * @return 库存确认结果
	 */
	InventoryResponse commit(InventoryRequest request);

	/**
	 * 释放锁定库存，如请求中没有明细，则表示释放全部。步骤如下：
	 * 1、使用document+version判断是否有锁定记录；（考虑将document+version+status放入Redis加速）
	 * 2、如无预留状态，返回错误；
	 * 3、如找到预留状态，将库存锁定释放；
	 * 4、更新库存数据；
	 * 5、抛出库存库存释放事件（扔给MQ去处理）
	 *
	 * @param inventoryRequest 库存释放请求信息，如请求信息中没有明细，则表示释放全部。
	 * @return 库存释放结果
	 */
	InventoryResponse release(InventoryRequest request);

	/**
	 * 订单履行库存
	 */
	InventoryResponse fulfillment(InventoryRequest request);

	/**
	 * 增加库存（RDC库存发生变化，需要执行库存增加任务）
	 */
	InventoryResponse increase(InventoryRequest request);

	/**
	 * 减少库存（RDC库存发生变化，需要执行库存减少任务）
	 */
	InventoryResponse reduce(InventoryRequest request);

	/**
	 * 全量同步SAP库存
	 * @param inventoryRequest
	 */
	BalanceResponse refresh(BalanceRequest request);

	/**
	 * 清除Refresh请求
	 */
	boolean deleteRefreshRequest(String idx);

	/**
	 * 清除Increase请求
	 */
	boolean deleteIncreaseRequest(String idx);

	/**
	 * 清除Reduce请求
	 */
	boolean deleteReduceRequest(String idx);

	/**
	 * 清除Release请求
	 */
	boolean deleteReleaseRequest(String idx);

	/**
	 * 清除Commit请求
	 */
	boolean deleteCommitRequest(String idx);

	/**
	 * 库存日结
	 */
	boolean dayEnd(String yesterday, String today);

	/**
	 * 按配置更新实际库存发生仓库及库区
	 */
	InventoryResponse updateRouteStockCode(InventoryRequest request);

	/**
	 * 按配置更新实际库存履行仓库及库区
	 */
	InventoryResponse updateFulfillmentStockCode(InventoryRequest request);
}
