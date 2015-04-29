package com.decade.framework.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.text.TextUtils;

import com.decade.framework.kit.DZSDCardOperate;

/**
 * @description: json缓存父类
 * @author: Decade
 * @date: 2013-4-27
 */
public class DZBaseJsonFileCache extends DZBaseFileCache{
	

	public void saveCache(File jsonCacheDir, String response, String url) {
		if (!TextUtils.isEmpty(response)) {
			File file = getFile(jsonCacheDir, url);
			writeToSDcardFile(file, response);
		}
	}

	public boolean isExpiredCache(File jsonCacheDir, String url, long mTimeDiff) {
		if (!TextUtils.isEmpty(url)) {
			String filename = url.hashCode() + "";
			File[] files = jsonCacheDir.listFiles();
			for (File f : files) {
				if (f.getName().equals(filename)) {
					if (System.currentTimeMillis() - f.lastModified() > mTimeDiff) {
						return true;
					}

				}
			}
		}
		return false;
	}

	public String selectCache(File jsonCacheDir, String url) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		String filename = url.hashCode() + "";
		String result = null;
		File[] files = jsonCacheDir.listFiles();
		for (File f : files) {
			if (f.getName().equals(filename)) {
				result = readContentFromFile(f);
			}
		}
		return result;
	}

	public synchronized void setFileExpired(File jsonCacheDir, String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		String filename = url.hashCode() + "";
		File[] files = jsonCacheDir.listFiles();
		for (File f : files) {
			if (f.getName().equals(filename)) {
				f.setLastModified(0);
			}
		}
	}

	public synchronized void writeToSDcardFile(File file, String szOutText) {
		// 获取扩展SD卡设备状态
		String sDStateString = android.os.Environment.getExternalStorageState();
		// 拥有可读可写权限
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			FileOutputStream outputStream = null;
			try {
				// 判断是否存在,不存在则创建
				if (!file.exists()) {
					file.createNewFile();
				}
				// 写数据
				outputStream = new FileOutputStream(file);
				outputStream.write(szOutText.getBytes());
			} catch (Exception e) {
				throw new RuntimeException("IOException occurred. ", e);
			} finally {
				DZSDCardOperate.closeSilently(outputStream);
			}
		}// end of if(MEDIA_MOUNTED)
	}

	public String readContentFromFile(File file) {

		String content = null;
		// 判断文件是否存在,存在的情況下才去讀該文件
		FileInputStream inputStream = null;
		if (file.exists()) {
			try {
				// 读数据
				inputStream = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				inputStream.read(buffer);
				content = new String(buffer);
			} catch (Exception e) {
				throw new RuntimeException("IOException occurred. ", e);
			} finally {
				DZSDCardOperate.closeSilently(inputStream);
			}
		}// end of if(myFile)
		return content;
	}
}