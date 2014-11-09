package me.linkcube.taku.ui.ota;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.linkcube.taku.AppConst.PATH;

import android.util.Log;

import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;

import custom.android.util.FormatUtils;

public class OTAManager {

	private static BluetoothMsgListener bluetoothMsgListener;

	public static void startReceiveData() {
		BTManager.getInstance().startReceiveData(new IReceiveData() {

			@Override
			public void receiveData(int length, byte[] buffer) {
				byte[] buf_data = new byte[length];
				for (int i = 0; i < length; i++) {
					buf_data[i] = buffer[i];
				}
				String getBlutToothString = FormatUtils.bytesToHexString(buf_data);
				Log.d("OTAManager", "GET_BLUETOOTH_FRAME:" + getBlutToothString);
				bluetoothMsgListener.receiveDataUpdateUI(getBlutToothString);
			}
		});
	}
	
	/**
	 * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath 　　
	 */
	public static void saveFile(String fileName,String toSaveString) {
		try {
			File saveFile = new File(PATH.SDCARD_OTA_PATH+fileName+".txt");
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

	public static void setBluetoothMsgListener(
			BluetoothMsgListener bluetoothMsgListener) {
		OTAManager.bluetoothMsgListener = bluetoothMsgListener;
	}

	public interface BluetoothMsgListener {
		void receiveDataUpdateUI(String bluetoothMsg);
	}
}
