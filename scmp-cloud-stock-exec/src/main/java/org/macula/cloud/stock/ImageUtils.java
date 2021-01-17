package org.macula.cloud.stock;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图像转换助手
 */
public class ImageUtils {
	/**
	 * 地址图片转化为Base64格式
	 * @param image
	 * @param format
	 * @return
	 */
	public static String getBase64Image(String path) {
		try (InputStream inputStream = ImageUtils.class.getResourceAsStream(path)) {
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			Base64.Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(data);
		} catch (IOException e) {
			// IGNORE
		}
		return null;
	}

	/**
	 * 图片转化为Base64格式
	 * @param image
	 * @param format (eg:png)
	 * @return
	 */
	public static String getBase64Image(BufferedImage image, String format) {
		return getBase64Image(image, format, Long.MAX_VALUE);
	}

	/**
	 * 图片转化为Base64格式
	 * @param image
	 * @param format (eg:png)
	 * @return
	 */
	public static String getBase64Image(BufferedImage image, String format, long limitSize) {
		try {
			image = thumbnail(image, format, limitSize);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, format, out);
			Base64.Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(out.toByteArray());
		} catch (IOException e) {
			// IGNORE
		}
		return null;
	}

	/**
	 * 
	 * @param base64
	 * @return
	 */
	public static String getImageMd5(String base64) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] out = decoder.decode(base64);
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		byte[] digest = md5.digest(out);
		return byte2hex(digest);
	}

	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return StringUtils.upperCase(hs.toString());
	}

	/**
	 * @param iamges 要拼接的图片清单
	 * @param type: 1 横向拼接， 2 纵向拼接
	 * @description 图片拼接  
	 */
	public static BufferedImage mergeImage(BufferedImage[] images, int type) {
		if (images == null) {
			return null;
		}
		int len = images.length;
		if (len == 1) {
			return images[0];
		}
		int[][] imagesArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			imagesArrays[i] = new int[width * height];
			imagesArrays[i] = images[i].getRGB(0, 0, width, height, imagesArrays[i], 0, width);
		}
		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			if (type == 1) {
				newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
				newWidth += images[i].getWidth();
			} else if (type == 2) {// 纵向
				newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
				newHeight += images[i].getHeight();
			}
		}
		if (type == 1 && newWidth < 1) {
			return null;
		}
		if (type == 2 && newHeight < 1) {
			return null;
		}

		// 生成新图片
		BufferedImage mergedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		int height_i = 0;
		int width_i = 0;
		for (int i = 0; i < images.length; i++) {
			if (type == 1) {
				mergedImage.setRGB(width_i, 0, images[i].getWidth(), newHeight, imagesArrays[i], 0, images[i].getWidth());
				width_i += images[i].getWidth();
			} else if (type == 2) {
				mergedImage.setRGB(0, height_i, newWidth, images[i].getHeight(), imagesArrays[i], 0, newWidth);
				height_i += images[i].getHeight();
			}
		}
		return mergedImage;
	}

	public static BufferedImage thumbnail(BufferedImage origin, String format, long targetSize) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(origin, format, out);
		} catch (IOException e) {
			// IGNORE
		}
		if (targetSize >= out.size()) {
			return origin;
		}

		try {
			double scale = (targetSize * 1.0 / out.size());
			return Thumbnails.of(origin).scale(scale).asBufferedImage();
		} catch (IOException e) {
			return origin;
		}
	}
}
