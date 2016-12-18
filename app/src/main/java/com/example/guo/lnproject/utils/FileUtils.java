package com.example.guo.lnproject.utils;

import java.io.File;

import android.os.Environment;

public class FileUtils {
	/**
	 * 获取数据库的路径
	 * @param dbFileName
	 * @return
	 */
	public static String getDBPath(String dbFileName){
		String path = "";
		
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			path = Environment.getExternalStorageDirectory().getPath()
		            + File.separator + "littlenurse";
			File file = new File(path);
			if (!file.exists()){
				file.mkdirs();
			}
			path = path + File.separator + dbFileName;
		}else{
			path = dbFileName;
		}
		return path;
	}
}
