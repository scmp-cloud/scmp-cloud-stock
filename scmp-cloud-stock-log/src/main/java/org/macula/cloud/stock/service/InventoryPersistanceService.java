package org.macula.cloud.stock.service;

import javax.transaction.Transactional;

import org.macula.cloud.stock.domain.StockRequestDetail;
import org.macula.cloud.stock.domain.StockRequestMaster;
import org.macula.cloud.stock.event.InventoryCommitEvent;
import org.macula.cloud.stock.event.InventoryFulfillmentEvent;
import org.macula.cloud.stock.event.InventoryIncreaseEvent;
import org.macula.cloud.stock.event.InventoryReduceEvent;
import org.macula.cloud.stock.event.InventoryRefreshEvent;
import org.macula.cloud.stock.event.InventoryReleaseEvent;
import org.macula.cloud.stock.event.InventoryReserveEvent;
import org.macula.cloud.stock.feign.StockFeignClient;
import org.macula.cloud.stock.repository.StockRequestMasterRepository;
import org.macula.cloud.stock.util.InventoryUtils;
import org.macula.cloud.stock.util.SystemUtils;
import org.macula.cloud.stock.vo.BalanceQuantity;
import org.macula.cloud.stock.vo.BalanceRequest;
import org.macula.cloud.stock.vo.InventoryRequest;
import org.macula.cloud.stock.vo.ProductQuantity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryPersistanceService {

	private StockFeignClient stockClient;
	private StockRequestMasterRepository repository;

	@Transactional
	public void handleInventoryIncreaseEvent(InventoryIncreaseEvent event) {
		ResponseEntity<Boolean> response = stockClient.deleteIncreaseRequest(InventoryUtils.getUniqueIndexKey(event.getRequest()));
		if (log.isInfoEnabled()) {
			log.info("InventoryIncreaseEvent: {}, response: {} ", event, response);
		}

		if (response != null && response.getBody()) {
			InventoryRequest request = event.getRequest();
			StockRequestMaster master = new StockRequestMaster();
			master.setClientId(request.getClientId());
			master.setChannel(request.getChannel());
			master.setDocument(request.getDocument());
			master.setVersion(request.getVersion());
			master.setAccDate(request.getAccDate());
			master.setIncreasedTime(SystemUtils.getDate(request.getTimestamp()));

			for (ProductQuantity d : request.getDetails()) {
				StockRequestDetail detail = new StockRequestDetail();
				detail.setApplyWhCode(d.getApplyWhCode());
				detail.setApplyLocCode(d.getApplyLocCode());
				detail.setActuallyWhCode(d.getActuallyWhCode());
				detail.setActuallyLocCode(d.getActuallyLocCode());
				detail.setProductCode(d.getProductCode());
				detail.setApplyQty(d.getApplyQuantity());
				detail.setIncreasedQty(d.getActuallyQuantity());
				master.addDetail(detail);
			}
			repository.save(master);
		}
	}

	@Transactional
	public void handleInventoryReduceEvent(InventoryReduceEvent event) {
		ResponseEntity<Boolean> response = stockClient.deleteReduceRequest(InventoryUtils.getUniqueIndexKey(event.getRequest()));
		if (log.isInfoEnabled()) {
			log.info("InventoryReduceEvent: {}, response: {} ", event, response);
		}

		if (response != null && response.getBody()) {
			InventoryRequest request = event.getRequest();
			StockRequestMaster master = new StockRequestMaster();
			master.setClientId(request.getClientId());
			master.setChannel(request.getChannel());
			master.setDocument(request.getDocument());
			master.setVersion(request.getVersion());
			master.setAccDate(request.getAccDate());
			master.setReducedTime(SystemUtils.getDate(request.getTimestamp()));

			for (ProductQuantity d : request.getDetails()) {
				StockRequestDetail detail = new StockRequestDetail();
				detail.setApplyWhCode(d.getApplyWhCode());
				detail.setApplyLocCode(d.getApplyLocCode());
				detail.setActuallyWhCode(d.getActuallyWhCode());
				detail.setActuallyLocCode(d.getActuallyLocCode());
				detail.setProductCode(d.getProductCode());
				detail.setApplyQty(d.getApplyQuantity());
				detail.setReducedQty(d.getActuallyQuantity());
				master.addDetail(detail);
			}
			repository.save(master);
		}
	}

	@Transactional
	public void handleInventoryReserveEvent(InventoryReserveEvent event) {
		ResponseEntity<Boolean> response = ResponseEntity.ok(Boolean.TRUE);
		if (log.isInfoEnabled()) {
			log.info("InventoryReserveEvent: {}, response: {} ", event, response);
		}

		if (response != null && response.getBody()) {
			InventoryRequest request = event.getRequest();
			StockRequestMaster master = new StockRequestMaster();
			master.setClientId(request.getClientId());
			master.setChannel(request.getChannel());
			master.setDocument(request.getDocument());
			master.setVersion(request.getVersion());
			master.setAccDate(request.getAccDate());
			master.setReleasedTime(SystemUtils.getDate(request.getTimestamp()));

			for (ProductQuantity d : request.getDetails()) {
				StockRequestDetail detail = new StockRequestDetail();
				detail.setApplyWhCode(d.getApplyWhCode());
				detail.setApplyLocCode(d.getApplyLocCode());
				detail.setActuallyWhCode(d.getActuallyWhCode());
				detail.setActuallyLocCode(d.getActuallyLocCode());
				detail.setProductCode(d.getProductCode());
				detail.setApplyQty(d.getApplyQuantity());
				detail.setReleasedQty(d.getActuallyQuantity());
				master.addDetail(detail);
			}
			repository.save(master);
		}
	}

	@Transactional
	public void handleInventoryCommitEvent(InventoryCommitEvent event) {
		ResponseEntity<Boolean> response = stockClient.deleteCommitRequest(InventoryUtils.getUniqueIndexKey(event.getRequest()));
		if (log.isInfoEnabled()) {
			log.info("InventoryCommitEvent: {}, response: {} ", event, response);
		}

		if (response != null && response.getBody()) {
			InventoryRequest request = event.getRequest();
			StockRequestMaster master = new StockRequestMaster();
			master.setClientId(request.getClientId());
			master.setChannel(request.getChannel());
			master.setDocument(request.getDocument());
			master.setVersion(request.getVersion());
			master.setAccDate(request.getAccDate());
			master.setCommitedTime(SystemUtils.getDate(request.getTimestamp()));

			for (ProductQuantity d : request.getDetails()) {
				StockRequestDetail detail = new StockRequestDetail();
				detail.setApplyWhCode(d.getApplyWhCode());
				detail.setApplyLocCode(d.getApplyLocCode());
				detail.setActuallyWhCode(d.getActuallyWhCode());
				detail.setActuallyLocCode(d.getActuallyLocCode());
				detail.setProductCode(d.getProductCode());
				detail.setApplyQty(d.getApplyQuantity());
				detail.setCommitedQty(d.getActuallyQuantity());
				master.addDetail(detail);
			}
			repository.save(master);
		}
	}

	@Transactional
	public void handleInventoryReleaseEvent(InventoryReleaseEvent event) {
		ResponseEntity<Boolean> response = stockClient.deleteReleaseRequest(InventoryUtils.getUniqueIndexKey(event.getRequest()));
		if (log.isInfoEnabled()) {
			log.info("InventoryReleaseEvent: {}, response: {} ", event, response);
		}

		if (response != null && response.getBody()) {
			InventoryRequest request = event.getRequest();
			StockRequestMaster master = new StockRequestMaster();
			master.setClientId(request.getClientId());
			master.setChannel(request.getChannel());
			master.setDocument(request.getDocument());
			master.setVersion(request.getVersion());
			master.setAccDate(request.getAccDate());
			master.setReleasedTime(SystemUtils.getDate(request.getTimestamp()));

			for (ProductQuantity d : request.getDetails()) {
				StockRequestDetail detail = new StockRequestDetail();
				detail.setApplyWhCode(d.getApplyWhCode());
				detail.setApplyLocCode(d.getApplyLocCode());
				detail.setActuallyWhCode(d.getActuallyWhCode());
				detail.setActuallyLocCode(d.getActuallyLocCode());
				detail.setProductCode(d.getProductCode());
				detail.setApplyQty(d.getApplyQuantity());
				detail.setReleasedQty(d.getActuallyQuantity());
				master.addDetail(detail);
			}
			repository.save(master);
		}
	}

	public void handleInventoryFulfillmentEvent(InventoryFulfillmentEvent event) {
		if (log.isInfoEnabled()) {
			log.info("InventoryFulfillmentEvent: {}", event);
		}

		InventoryRequest request = event.getRequest();
		StockRequestMaster master = new StockRequestMaster();
		master.setClientId(request.getClientId());
		master.setChannel(request.getChannel());
		master.setDocument(request.getDocument());
		master.setVersion(request.getVersion());
		master.setAccDate(request.getAccDate());
		master.setReleasedTime(SystemUtils.getDate(request.getTimestamp()));

		for (ProductQuantity d : request.getDetails()) {
			StockRequestDetail detail = new StockRequestDetail();
			detail.setProductCode(d.getProductCode());
			detail.setApplyWhCode(d.getApplyWhCode());
			detail.setApplyLocCode(d.getApplyLocCode());
			detail.setFulfillmentWhCode(d.getActuallyWhCode());
			detail.setFulfillmentLocCode(d.getActuallyLocCode());
			detail.setApplyQty(d.getApplyQuantity());
			detail.setFulfillmentQty(d.getActuallyQuantity());
			master.addDetail(detail);
		}
		repository.save(master);
	}

	@Transactional
	public void handleInventoryRefreshEvent(InventoryRefreshEvent event) {
		ResponseEntity<Boolean> response = stockClient.deleteRefreshRequest(InventoryUtils.getUniqueIndexKey(event.getRequest()));
		log.info("InventoryRefreshEvent: {}, response: {} ", event, response);
		if (log.isInfoEnabled()) {
			log.info("InventoryCommitEvent: {}, response: {} ", event, response);
		}

		if (response != null && response.getBody()) {
			BalanceRequest request = event.getRequest();
			StockRequestMaster master = new StockRequestMaster();
			master.setClientId(request.getClientId());
			master.setChannel(request.getChannel());
			master.setDocument(request.getDocument());
			master.setVersion(request.getVersion());
			master.setAccDate(request.getAccDate());
			master.setInitializedTime(SystemUtils.getDate(request.getTimestamp()));

			for (BalanceQuantity d : request.getDetails()) {
				StockRequestDetail detail = new StockRequestDetail();
				detail.setApplyWhCode(d.getWhCode());
				detail.setApplyLocCode(d.getLocCode());
				detail.setActuallyWhCode(d.getWhCode());
				detail.setActuallyLocCode(d.getLocCode());
				detail.setProductCode(d.getProductCode());
				detail.setInitializedQty(d.getInitializedQty());
				detail.setBalanceQty(d.getBalanceQty());
				detail.setReservedQty(d.getReservedQty());
				detail.setReleasedQty(d.getReleasedQty());
				detail.setCommitedQty(d.getCommitedQty());
				detail.setIncreasedQty(d.getIncreasedQty());
				detail.setReducedQty(d.getReducedQty());
				master.addDetail(detail);
			}
			repository.save(master);
		}
	}

}
