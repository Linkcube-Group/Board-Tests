package me.linkcube.skeatest.ui.skeasensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.linkcube.skeatest.AppConst.PATH;

import android.content.Context;
import android.telephony.TelephonyManager;

public class GraphManager {

	private static String TAG = "GraphManager";
	private static Context context;

	public static void init(Context context) {
		GraphManager.context = context;
		File temp = new File("/sdcard/skeatest");// 自已项目 文件夹
		if (!temp.exists()) {
			temp.mkdir();
		}
	}

	/**
	 * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath 　　
	 */
	public static void saveFile(String fileName,String toSaveString) {
		try {
			File saveFile = new File(PATH.SDCARD_SENSOR_PATH+fileName+".txt");
			if (!saveFile.exists()) {
				File dir = new File(saveFile.getParent());
				dir.mkdirs();
				saveFile.createNewFile();
			}
			FileOutputStream outStream;
			outStream = new FileOutputStream(saveFile);
			outStream.write(toSaveString.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getMillSecond() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
		return simpleDateFormat.format(date);
	}
	
	public static String getFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		return simpleDateFormat.format(date);
	}

	public static String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
}
