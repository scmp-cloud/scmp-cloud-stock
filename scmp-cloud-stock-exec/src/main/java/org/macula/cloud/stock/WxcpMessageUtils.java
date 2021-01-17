package org.macula.cloud.stock;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * 企业微信机器人消息构建助手
 */
public class WxcpMessageUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String getTextMessage(String text, List<String> person, List<String> mobiles) {
		TextMessage message = new TextMessage();
		message.text.mentioned_list = person;
		message.text.mentioned_mobile_list = mobiles;
		return writeValueAsString(message);
	}

	public static String getMarkdownMessage(String markdown) {
		MarkdownMessage message = new MarkdownMessage();
		message.markdown.content = markdown;
		return writeValueAsString(message);
	}

	public static String getImageMessage(String base64) {
		ImageMessage message = new ImageMessage();
		message.image.base64 = base64;
		message.image.md5 = ImageUtils.getImageMd5(base64);
		return writeValueAsString(message);
	}

	public static String getNewsMessage(ArticleContent... content) {
		NewsMessage message = new NewsMessage();
		for (ArticleContent articleContent : content) {
			message.news.articles.add(articleContent);
		}
		return writeValueAsString(message);
	}

	public static String getWebhookUrl(String key) {
		return String.format("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?debug=1&key=%s", key);
	}

	public static String getUploadImageUrl(String key) {
		return String.format("https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media?key=%s&type=file", key);
	}

	private static String writeValueAsString(Object message) {
		try {
			return objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	@Getter
	@Setter
	public static class TextMessage {
		private String msgtype = "text";
		private TextContent text = new TextContent();
	}

	@Getter
	@Setter
	public static class TextContent {
		private String content;
		private List<String> mentioned_list;
		private List<String> mentioned_mobile_list;
	}

	@Getter
	@Setter
	public static class MarkdownMessage {
		private String msgtype = "markdown";
		private MarkdownContent markdown = new MarkdownContent();
	}

	@Getter
	@Setter
	public static class MarkdownContent {
		private String content;
	}

	@Getter
	@Setter
	public static class ImageMessage {
		private String msgtype = "image";
		private ImageContent image = new ImageContent();
	}

	@Getter
	@Setter
	public static class ImageContent {
		private String base64;
		private String md5;
	}

	@Getter
	@Setter
	public static class NewsMessage {
		private String msgtype = "news";
		private NewsContet news = new NewsContet();
	}

	@Getter
	@Setter
	public static class NewsContet {
		private List<ArticleContent> articles = new ArrayList<ArticleContent>();
	}

	@Getter
	@Setter
	public static class ArticleContent {
		private String title;
		private String description;
		private String url;
		private String picurl;
	}

	@Getter
	@Setter
	public static class FileMessage {
		private String msgtype = "file";
		private FileContent file = new FileContent();
	}

	@Getter
	@Setter
	public static class FileContent {
		private String media_id;
	}

	@Getter
	@Setter
	public static class FileUploadResult {
		private long errcode;
		private String errmsg;
		private String type;
		private String media_id;
		private String created_at;
	}
}
