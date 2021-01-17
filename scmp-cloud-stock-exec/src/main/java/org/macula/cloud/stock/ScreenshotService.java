package org.macula.cloud.stock;

import static java.lang.Thread.currentThread;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.macula.cloud.stock.configure.StockConfig;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScreenshotService {

	private StockConfig config;

	/**
	 * 抓取页面内容形成图片
	 */
	public BufferedImage createImage(ScreenshotSettings settings) throws IOException {
		System.setProperty("webdriver.chrome.driver", config.getChromeLocation());
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--hide-scrollbars");
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments(String.format("--window-size=%d,%d", settings.getWidth(), settings.getHeight()));

		if (settings.getUserAgent() != null) {
			chromeOptions.addArguments(String.format("--user-agent=\"%s\"", settings.getUserAgent()));
		}

		List<String> arguments = settings.getArguments();
		if (arguments != null) {
			chromeOptions.addArguments(arguments);
		}

		if (settings.getDeviceName() != null) {
			Map<String, String> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", settings.getDeviceName());
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		}

		final ChromeDriver driver = new ChromeDriver(chromeOptions);

		List<org.macula.cloud.stock.Cookie> cookies = settings.getCookies();
		if (cookies != null) {
			driver.get(settings.getUrl());
			driver.manage().deleteAllCookies();
			new WebDriverWait(driver, 300).until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
			cookies.forEach(c -> {
				Cookie cookie = new Cookie(c.getName(), c.getValue(), c.getDomain(), c.getPath(), c.getExpiry(), c.isSecure(), c.isHttpOnly());
				driver.manage().addCookie(cookie);
			});
		}

		driver.navigate().to(settings.getUrl());
		new WebDriverWait(driver, 300).until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

		String elements = settings.getRemoveClassNames();
		if (elements != null) {
			for (String el : elements.split(",")) {
				String cmd = String.format("try { document.getElementsByClassName('%s')[0].remove();} catch(e) { }", el);
				driver.executeScript(cmd);
			}
		}

		elements = settings.getRemoveIds();
		if (elements != null) {
			for (String el : elements.split(",")) {
				String cmd = String.format("try { document.getElementById('%s').remove();} catch(e) { }", el);
				driver.executeScript(cmd);
			}
		}
		int pageHeight = getFullHeight(driver);
		int viewportHeight = getWindowHeight(driver);

		int pages = (pageHeight / viewportHeight) + (pageHeight % viewportHeight > 0 ? 1 : 0);
		int lastPageOverlap = pages * viewportHeight - pageHeight;

		BufferedImage[] images = new BufferedImage[pages];

		for (int i = 0; i < pages; i++) {
			scrollVertically(driver, viewportHeight * i);
			waitForScrolling(settings.getTimeout());

			File file = driver.getScreenshotAs(OutputType.FILE);
			BufferedImage shot = ImageIO.read(file);
			if (i == pages - 1 && lastPageOverlap > 0) {
				// 最后一屏要裁剪，裁剪量要根据截屏实际图片大小与截屏窗口像素进行缩放
				int overlap = lastPageOverlap * shot.getHeight() / viewportHeight;
				shot = shot.getSubimage(0, overlap, shot.getWidth(), shot.getHeight() - overlap);
			}
			images[i] = shot;
		}
		return ImageUtils.mergeImage(images, 2);
	}

	private void waitForScrolling(long scrollTimeout) {
		try {
			Thread.sleep(scrollTimeout);
		} catch (InterruptedException e) {
			throw new IllegalStateException("Exception while waiting for scrolling", e);
		}
	}

	/**
	 * 获取整页高度
	 * @return
	 */
	public int getFullHeight(WebDriver driver) {
		return ((Number) execute(PAGE_HEIGHT_JS, driver)).intValue();
	}

	/**
	 * 获取整页宽度
	 */
	public int getFullWidth(WebDriver driver) {
		return ((Number) execute(VIEWPORT_WIDTH_JS, driver)).intValue();
	}

	/**
	 * 获取窗口高度
	 */
	public int getWindowHeight(WebDriver driver) {
		return ((Number) execute(VIEWPORT_HEIGHT_JS, driver)).intValue();
	}

	/**
	 * 屏幕滚动到
	 */
	protected void scrollVertically(JavascriptExecutor js, int scrollY) {
		js.executeScript("scrollTo(0, arguments[0]); return [];", scrollY);
	}

	/**
	 * 执行script语句
	 */
	@SuppressWarnings("unchecked")
	public static <T> T execute(String path, WebDriver driver, Object... args) {
		try {
			String script = IOUtils.toString(currentThread().getContextClassLoader().getResourceAsStream(path), StandardCharsets.UTF_8);
			// noinspection unchecked
			return (T) ((JavascriptExecutor) driver).executeScript(script, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final String PAGE_HEIGHT_JS = "js/page_height.js";
	public static final String VIEWPORT_HEIGHT_JS = "js/viewport_height.js";
	public static final String VIEWPORT_WIDTH_JS = "js/viewport_width.js";
}
