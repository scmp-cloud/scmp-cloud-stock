package org.macula.cloud.stock.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InventoryResponse extends StockResponse {

	private static final long serialVersionUID = 1L;

	/**
	 * 原库存请求
	 */
	private InventoryRequest request;

	public static InventoryResponse success(InventoryRequest request) {
		InventoryResponse response = new InventoryResponse();
		response.setSuccess(true);
		response.setRequest(request);
		response.setCode("1");
		return response;
	}

	public static InventoryResponse failed(String code, String message, InventoryRequest request) {
		InventoryResponse response = new InventoryResponse();
		response.setRequest(request);
		response.setSuccess(false);
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

	/**
	 * 是否完全满足需要
	 */
	public boolean isActuallySufficient() {
		if (request != null && request.getDetails() != null) {
			for (ProductQuantity detail : request.getDetails()) {
				if (detail.getActuallyQuantity().compareTo(detail.getApplyQuantity()) < 0) {
					return false;
				}
			}
		}
		return true;
	}
}
