package com.decade.framework.cache;

import java.io.File;

import android.text.TextUtils;

import com.decade.framework.kit.DZSDCardOperate;

/**
 * @description: 所有缓存基础类
 * @author: Decade
 * @date: 2013-4-27
 */
public class DZBaseFileCache {
	
	public File getFile(File cacheDir, String url) {
		if (TextUtils.isEmpty(url))
			return null;
		String filename = url.hashCode() + "";
		File f = new File(cacheDir, filename);
		DZSDCardOperate.updateFileTime(f);
		return f;
	}

	public void clear(File cacheDir) {
		File[] files = cacheDir.listFiles();
		for (File f : files) {
			f.delete();
		}
	}
}
