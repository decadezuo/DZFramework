package com.decade.framework.cache;

import java.io.File;

import com.decade.framework.DZApplication;
import com.decade.framework.kit.DZSDCardOperate;

/**
 * @description: json缓存处理类
 * @author: Decade
 * @date: 2013-9-16
 * 
 */
public class DZJsonFileCache extends DZBaseJsonFileCache {
	private static DZJsonFileCache jsonFileCache = null;
	private static File jsonCacheDir = null;
	private static long mTimeDiff = 7 * 24 * 60 * 60 * 1000; // 七天

	public DZJsonFileCache() {
		// Find the dir to save cached images
		if (DZSDCardOperate.isSDCardMounted()) {
			jsonCacheDir = new File(DZSDCardOperate.getFilePath(),
					"Android/Cache/"
							+ DZApplication.getAppContext().getPackageName()
							+ "/Json_Cache/");
			DZSDCardOperate.removeExpiredCache(jsonCacheDir, mTimeDiff);
		} else {
			jsonCacheDir = DZApplication.getAppContext().getCacheDir();
		}
		if (jsonCacheDir != null) {
			if (!jsonCacheDir.exists())
				jsonCacheDir.mkdirs();
		}
	}

	public static DZJsonFileCache getJsonFileCache() {
		if (jsonFileCache == null) {
			jsonFileCache = new DZJsonFileCache();
		}
		return jsonFileCache;
	}

	public File getFile(String url) {
		File f = getFile(jsonCacheDir, url);
		return f;
	}

	public void clear() {
		clear(jsonCacheDir);
	}

	public void saveCache(String response, String url) {
		saveCache(jsonCacheDir, response, url);
	}

	public boolean isExpiredCache(String url, long mTimeDiff) {
		return isExpiredCache(jsonCacheDir, url, mTimeDiff);
	}

	public String selectCache(String url) {
		return selectCache(jsonCacheDir, url);
	}

	public void setFileExpired(String url) {
		setFileExpired(jsonCacheDir, url);
	}

	/**
	 * 设置缓存自动清理间隔时间
	 * 
	 * @param time
	 */
	public static void setTimeDiff(int time) {
		mTimeDiff = time;
	}

	/**
	 * 设置json数据缓存目录
	 * 
	 * @param dir
	 */
	public static void setJsonCacheDir(File dir) {
		jsonCacheDir = dir;
	}
}