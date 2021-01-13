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
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
public class ImageCaptchService {

	public BufferedImage createImage(ImageCaptch settings) {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
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

		List<String> headers = settings.getHeaders();
		if (headers != null) {
			chromeOptions.addArguments(headers);
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

		int scrollTimes = (pageHeight / viewportHeight) + (pageHeight % viewportHeight > 0 ? 1 : 0);
		BufferedImage[] screenshotImages = new BufferedImage[scrollTimes];

		for (int n = 0; n < scrollTimes; n++) {
			scrollVertically(driver, viewportHeight * n);
			waitForScrolling(settings.getTimeout());

			File file = driver.getScreenshotAs(OutputType.FILE);
			try {
				screenshotImages[n] = ImageIO.read(file);
			} catch (IOException e) {
				// IGNORE
			}
		}
		return ImageUtils.mergeImage(screenshotImages, 2);
	}

	private void waitForScrolling(long scrollTimeout) {
		try {
			Thread.sleep(scrollTimeout);
		} catch (InterruptedException e) {
			throw new IllegalStateException("Exception while waiting for scrolling", e);
		}
	}

	public int getFullHeight(WebDriver driver) {
		return ((Number) execute(PAGE_HEIGHT_JS, driver)).intValue();
	}

	public int getFullWidth(WebDriver driver) {
		return ((Number) execute(VIEWPORT_WIDTH_JS, driver)).intValue();
	}

	public int getWindowHeight(WebDriver driver) {
		return ((Number) execute(VIEWPORT_HEIGHT_JS, driver)).intValue();
	}

	protected int getCurrentScrollY(JavascriptExecutor js) {
		return ((Number) js.executeScript("var scrY = window.scrollY;" + "if(scrY){return scrY;} else {return 0;}")).intValue();
	}

	protected void scrollVertically(JavascriptExecutor js, int scrollY) {
		js.executeScript("scrollTo(0, arguments[0]); return [];", scrollY);
	}

	@SuppressWarnings("unchecked")
	public static <T> T execute(String path, WebDriver driver, Object... args) {
		try {
			String script = IOUtils.toString(currentThread().getContextClassLoader().getResourceAsStream(path), StandardCharsets.UTF_8);
			//noinspection unchecked
			return (T) ((JavascriptExecutor) driver).executeScript(script, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final String PAGE_HEIGHT_JS = "js/page_height.js";
	public static final String VIEWPORT_HEIGHT_JS = "js/viewport_height.js";
	public static final String VIEWPORT_WIDTH_JS = "js/viewport_width.js";
}
