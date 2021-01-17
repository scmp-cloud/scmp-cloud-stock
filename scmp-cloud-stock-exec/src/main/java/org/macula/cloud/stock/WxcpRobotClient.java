package org.macula.cloud.stock;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 企业微信机器人消息发送Client
 */
@Service
public class WxcpRobotClient {

	private RestTemplate restTemplate = new RestTemplate();

	public String sendMessage(String key, String message) {
		HttpHeaders httpHeaders = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
		httpHeaders.setContentType(type);
		HttpEntity<String> body = new HttpEntity<String>(message, httpHeaders);
		String result = restTemplate.postForObject(WxcpMessageUtils.getWebhookUrl(key), body, String.class);
		return result;
	}

}
