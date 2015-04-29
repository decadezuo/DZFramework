package com.decade.framework.kit;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;

import android.os.Environment;
import android.os.StatFs;

/**
 * @author Decade
 * 
 */
public class DZSDCardOperate {
	private static final int MB = 1024 * 1024;
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 3;
	private static final int CACHE_SIZE = 10;
	/**
	 * 　　* 寫入指定文件中的內容
	 * 　　* @param fileName 文件
	 * 　　* @param destDirStr 文件目錄
	 * 　　* @param szOutText 文件內容
	 * 　　* @return 　　
	 */
	public static String writeToSDcardFile(String fileName, String destDirStr,
			String szOutText) {
		// 获取扩展SD卡设备状态
		String sDStateString = android.os.Environment.getExternalStorageState();
		File myFile = null;
		// 拥有可读可写权限
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			FileOutputStream outputStream = null;
			try {
				// 获取扩展存储设备的文件目录

				File SDFile = android.os.Environment
						.getExternalStorageDirectory();
				// File destDir=new File("/sdcard/xmlfile");

				File destDir = new File(SDFile.getAbsolutePath() + destDirStr);
				if (!destDir.exists())
					destDir.mkdir();
				// Toast.makeText(SDCardTest., text, duration)
				// 打开文件
				myFile = new File(destDir + File.separator + fileName);
				// 判断是否存在,不存在则创建
				if (!myFile.exists()) {
					myFile.createNewFile();
				}
				// 写数据
				// String szOutText = "Hello, World!";
				outputStream = new FileOutputStream(myFile, true);
				outputStream.write(szOutText.getBytes());
			} catch (Exception e) {

				throw new RuntimeException("IOException occurred. ", e);
			} finally {
				closeSilently(outputStream);
			}
			// end of try
		}// end of if(MEDIA_MOUNTED)
		// 拥有只读权限
		else if (sDStateString
				.endsWith(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
			// 获取扩展存储设备的文件目录
			File SDFile = android.os.Environment.getExternalStorageDirectory();
			// 创建一个文件
			myFile = new File(SDFile.getAbsolutePath() + destDirStr
					+ File.separator + fileName);
			// 判断文件是否存在,存在的情況下才去讀該文件
			if (myFile.exists()) {
				FileInputStream inputStream = null;
				try {
					// 读数据
					inputStream = new FileInputStream(myFile);
					byte[] buffer = new byte[1024];
					inputStream.read(buffer);
				} catch (Exception e) {

					throw new RuntimeException("IOException occurred. ", e);
				} finally {
					closeSilently(inputStream);
				}
			}// end of if(myFile)
		}// end of if(MEDIA_MOUNTED_READ_ONLY)
		// end of func
		return myFile.toString();
	}

	/**
	 * 　　* 讀出指定文件中的內容
	 * 　　* @param fileName 文件
	 * 　　* @param destDirStr 文件目錄
	 * 　　* @return 返回文件內容
	 * 
	 */

	public static String readContentFromFile(String fileName, String destDirStr) {

		String content = null;
		// 获取扩展存储设备的文件目录
		File SDFile = android.os.Environment.getExternalStorageDirectory();
		// 创建一个文件
		File myFile = new File(SDFile.getAbsolutePath() + destDirStr
				+ File.separator + fileName);
		// 判断文件是否存在,存在的情況下才去讀該文件
		if (myFile.exists()) {
			FileInputStream inputStream = null;
			try {
				// 读数据
				inputStream = new FileInputStream(myFile);
				byte[] buffer = new byte[1024];
				inputStream.read(buffer);
				inputStream.close();
				content = new String(buffer);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeSilently(inputStream);

			}
		}
		return content;
	}

	/**
	 * 计算sdcard上的剩余空间
	 * 
	 * @return
	 */
	private static int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize())
				/ MB;
		return (int) sdFreeMB;
	}

	/**
	 * 修改文件的最后修改时间
	 * 
	 * @param dir
	 * @param fileName
	 */
	public static void updateFileTime(File file) {
		if (file != null) {
			long newModifiedTime = System.currentTimeMillis();
			file.setLastModified(newModifiedTime);
		}
	}

	/**
	 *计算存储目录下的文件大小，
	 * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件
	 * 
	 * @param dirPath
	 * @param filename
	 */
	public static void removeCache(File file) {
		// File dir = new File(dirPath);
		if (!file.exists())
			return;
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			// if(files[i].getName().contains(WHOLESALE_CONV)) {
			dirSize += files[i].length();
			// }
		}
		if (dirSize > CACHE_SIZE * MB
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());
			DZLog.i(DZSDCardOperate.class, "Clear some expiredcache files ");
			for (int i = 0; i < removeFactor; i++) {
				// if(files[i].getName().contains(WHOLESALE_CONV)) {
				files[i].delete();
				// }

			}

		}

	}

	/**
	 * 删除过期文件
	 * 
	 * @param dirPath
	 * @param filename
	 */
	public static void removeExpiredCache(final File file, long mTimeDiff) {
		if (!file.exists()) {
			return;
		}
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (System.currentTimeMillis() - f.lastModified() > mTimeDiff) {
				DZLog.i(DZSDCardOperate.class, "Clear some expiredcache files ");
				f.delete();
			}
		}

	}

	/**
	 * 根据文件的最后修改时间进行排序 *
	 */
	private static class FileLastModifSort implements Comparator<File> {
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * 创建文件路径
	 * 
	 * @param pathName
	 */
	public static void createFileDir(String pathName) {
		File f = new File(getFilePath() + pathName);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	public static String getFilePath() {
		return Environment.getExternalStorageDirectory().getPath() + "/";
	}

	/**
	 * 判断sd卡是否安装
	 * 
	 * @return
	 */
	public static boolean isSDCardMounted() {
		boolean ret = false;
		String status = Environment.getExternalStorageState();
		ret = status.equals(Environment.MEDIA_MOUNTED) ? true : false;
		return ret;
	}
	
    public static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
            c=null;
        } catch (Throwable t) {
        	t.printStackTrace();
        }
    }
}
