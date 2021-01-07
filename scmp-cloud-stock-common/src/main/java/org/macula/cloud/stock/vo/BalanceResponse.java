package org.macula.cloud.stock.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BalanceResponse extends StockResponse {

	private static final long serialVersionUID = 1L;

	/**
	 * 原库存请求
	 */
	private BalanceRequest request;

	public static BalanceResponse success(BalanceRequest request) {
		BalanceResponse response = new BalanceResponse();
		response.setSuccess(true);
		response.setRequest(request);
		response.setCode("1");
		return response;
	}

	public static BalanceResponse failed(String code, String message, BalanceRequest request) {
		BalanceResponse response = new BalanceResponse();
		response.setRequest(request);
		response.setSuccess(false);
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

}
