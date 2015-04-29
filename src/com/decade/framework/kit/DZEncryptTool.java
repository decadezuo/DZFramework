package com.decade.framework.kit;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.decade.framework.kit.DZLog;
/**
 * @description:
 * @author: Decade
 * @date: 2013-5-6
 */
public class DZEncryptTool {
	// 用于cipher的参数，提供一个随机数产生器
	private SecureRandom secureRandom;
	// 密匙工厂
	private SecretKeyFactory keyFactory;
	// 用来加密解密
	private Cipher cipher;
	private static DZEncryptTool _encrypt;
	public static final String KEY = "ajec2pf1gnv6xzbr";

	public DZEncryptTool() throws NoSuchAlgorithmException,
			NoSuchPaddingException {
		secureRandom = new SecureRandom();
		keyFactory = SecretKeyFactory.getInstance("DES");
		cipher = Cipher.getInstance("DES");
	}

	public static DZEncryptTool getInstance() {

		if (_encrypt == null) {
			try {
				_encrypt = new DZEncryptTool();
			} catch (Exception e) {
				e.printStackTrace();
				DZLog.e(DZEncryptTool.class, "DCEncryptTool init error");
			}
		}
		return _encrypt;
	}

	public String encrypt(String keyData, String str) {
		while (keyData.length() < 8) {
			keyData += "decade";
		}
		byte[] rawKeyData = keyData.getBytes();
		try {
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(rawKeyData);
			// DESKeySpec转换成一个SecretKey对象
			SecretKey key = keyFactory.generateSecret(dks);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
			// 现在，获取数据并加密
			byte data[] = str.getBytes();
			// 正式执行加密操作
			byte[] rawEncryptedData = cipher.doFinal(data);
			DZLog.i(getClass(), "encrypt success");
			return new String(rawEncryptedData, "ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String keyData, String encryptedData) {
		while (keyData.length() < 8) {
			keyData += "decade";
		}
		byte[] rawKeyData = keyData.getBytes();
		try {
			byte[] rawEncryptedData = encryptedData.getBytes("ISO-8859-1");
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(rawKeyData);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
			SecretKey key = keyFactory.generateSecret(dks);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(rawEncryptedData);
			DZLog.i(getClass(), "decrypt success");
			return new String(decryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
