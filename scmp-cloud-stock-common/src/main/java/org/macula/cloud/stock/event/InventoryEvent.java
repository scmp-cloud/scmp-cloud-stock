package org.macula.cloud.stock.event;

import java.io.Serializable;
import java.util.UUID;

import org.macula.cloud.stock.util.InventoryUtils;

import lombok.Data;

@Data
public class InventoryEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 
	 * 事件编号
	 */
	private String id;

	/** 
	 * 父事件关联编号
	 */
	private String pid;

	/** 
	 * 事件索引（将一组事件串起来形成一次事件索引）
	 */
	private String thread;

	/** 
	 * 创建时间
	 */
	private long createdTimestamp;

	/** 
	 * 发布时间
	 */
	private long publishedTimestamp;

	/** 
	 * 接收发布时间
	 */
	private long receivedTimestamp;

	/** 
	 * 开始处理时间
	 */
	private long beginProcessTimestamp;

	/** 
	 * 完成处理时间
	 */
	private long completeProcessedTimestamp;

	/** 
	 * 出错时间
	 */
	private long errorTimestamp;

	/**
	 * 出错消息
	 */
	private String errorMessage;

	/**
	 * 记录最终处理状态
	 */
	private String status;

	/**
	 * 事件产生人
	 */
	private String user;

	/**
	 * 事件实际类
	 */
	private String clazz = getClass().getSimpleName();

	/**
	 * 默认构造，使用UUID作为id的记录值
	 */
	public InventoryEvent() {
		this(InventoryUtils.getCurrentEvent());
	}

	/**
	 * 使用父StockEvent构造，创建父子关系
	 * @param parentEvent
	 */
	public InventoryEvent(InventoryEvent parentEvent) {
		this.id = UUID.randomUUID().toString();
		setParentStockEvent(parentEvent);
		markCreated();
		InventoryUtils.setCurrentEvent(this);
	}

	/**
	 * 设置父StockEvent，创建父子关系
	 * @param parentEvent
	 */
	public void setParentStockEvent(InventoryEvent parentEvent) {
		setPid(parentEvent == null ? null : parentEvent.getId());
		setThread(parentEvent == null ? UUID.randomUUID().toString() : parentEvent.getThread());
	}

	/**
	 * 标记创建时间
	 */
	public void markCreated() {
		createdTimestamp = System.currentTimeMillis();
		status = "CREATED";
	}

	/**
	 * 标记发布时间
	 */
	public void markPublished() {
		publishedTimestamp = System.currentTimeMillis();
		status = "PUBLISHED";
	}

	/**
	 * 标记接收时间
	 */
	public void markReceived() {
		receivedTimestamp = System.currentTimeMillis();
		status = "RECEIVED";
	}

	/**
	 * 标记开始处理时间
	 */
	public void markBeginProcess() {
		beginProcessTimestamp = System.currentTimeMillis();
		status = "BEGIN_PROCESS";
	}

	/**
	 * 标记完成处理时间
	 */
	public void markCompleteProcessed() {
		completeProcessedTimestamp = System.currentTimeMillis();
		status = "COMPLETE_PROCESSED";
	}

	public void markErrorOccured(String message) {
		errorTimestamp = System.currentTimeMillis();
		errorMessage = message;
		status = "ERROR";
	}
}
