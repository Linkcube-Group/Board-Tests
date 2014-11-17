package me.linkcube.skeatest;

public class AppConst {

	public static class KEY {

		/** 引导页展示标志位 */
		public static final String SHOW_GUIDE = "ShowGuide";

		/** 是否自动登录 */
		public static final String AUTO_LOGIN = "AutoLogin";

		/** 登录用户名 */
		public static final String USER_NAME = "UserName";

		/** 登录密码 */
		public static final String USER_PWD = "UserPwd";

		/** 原始密码 */
		public static final String OLD_USER_PWD = "OldUserPwd";
	}

	/**
	 * 软件更新
	 * 
	 */
	public static class AppUpdate {

		public static String CHECK_VERSION_URL = "http://115.29.175.17/version";

		public static String APK_UPDATE_FLAG = "apk_update_flag";// "0"表示不需要更新也不需要展示，“1”表示需要提示更新，“2”表示已经提示过，需要在setting页面展示

		public static String APK_NAME = "linkcube.apk";

		public static String APK_VERSION = "apk_version";

		public static String APK_SIZE = "apk_size";

		public static String APK_DESCRIPTION = "apk_description";

		public static String APK_DOWNLOAD_URL = "apk_download_url";

	}

	public static class Device {

		public static String DEVICE_NAME = "device_name";

		public static String DEVICE_ADDRESS = "device_address";
	}

	/**
	 * 有盟统计
	 * 
	 */
	public static class UmengEvent {

		public static String SHACK_MODE_EVENT = "shackmodeevent";

		public static String VOICE_MODE_EVENT = "voicemodeevent";

		public static String MIC_MODE_EVENT = "micmodeevent";

		public static String SEXPOSITION_MODE_EVENT = "sexpositionmodeevent";

		public static String IS_CONNECT_TOY = "isconnecttoy";

		public static String CONNECT_TOY_DURATION = "ConnectToyDuration";

	}

	public static class HttpUrl {
		public static final String BASE_URL = "http://112.124.22.252:8001/";
	}

	/**
	 * 网络请求错误时返回的标识
	 * 
	 * @author xinyang
	 * 
	 */
	public static class ErrorFlag {

		public static final int NETWORK_ERROR = -1;

		public static final int OTHER_ERROR = -2;
	}

	public static class ResponseKey {

		public static final String STATUS = "status";

		public static final String MSG = "msg";

	}

	public static class ParamKey {

		public static final String IMEI = "imei";

		public static final String FIlE = "file";

	}

	public static class GameFrame {
		public static final byte[] SPEED_FRAME = { 0x25, 0x2, 0x2, 0x0, 0x0,
				0x0, 0x0, 0x29 };
		public static final byte[] HEART_RATE_FRAME = { 0x25, 0x3, 0x0, 0x0,
				0x0, 0x0, 0x0, 0x28 };
		public static final byte[] PRESS_FRAME = { 0x26, 0x1, 0x0, 0x0, 0x0,
				0x0, 0x0, 0x26 };

		public static final byte[] INTO_OTA_FRAME = { 0x25, (byte) 0xFF,
				(byte) 0xAA, (byte) 0xAA, 0x0, 0x0, 0x0, 0x78 };

		public static final byte[] SHAKE_HAND_FRAME = { 0x0F };

		public static final byte[] READ_BL_FRAME = { 0x0F, 0x0, 0x0, 0x0, 0x04 };

		public static final byte[][] READ_MEMORY_FRAMES = { 
			{ 0x0F, 0x01, 0x00,0x00, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0xF9, (byte) 0xA3,0x04 },
			{ 0x0F, 0x01, (byte) 0x80,0x00, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0xD9, 0x77,0x04 },
			{ 0x0F, 0x01, 0x00,0x01, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0xA8, 0x09,0x04 },
			{ 0x0F, 0x01, (byte) 0x80,0x01, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0x88, (byte) 0xDD,0x04 },
			{ 0x0F, 0x01, 0x00,0x02, 0x00, 0x00, (byte) 0x80, 0x00, 0x7A, (byte) 0xE7,0x04 },
			{ 0x0F, 0x01, (byte) 0x80,0x02, 0x00, 0x00, (byte) 0x80, 0x00, 0x5A, 0x33,0x04 },
			{ 0x0F, 0x01, 0x00,0x03, 0x00, 0x00, (byte) 0x80, 0x00, 0x2B, 0x4D,0x04 },
			{ 0x0F, 0x01, (byte) 0x80,0x03, 0x00, 0x00, (byte) 0x80, 0x00, 0x0B, (byte) 0x99,0x04 },
			{ 0x0F, 0x01, 0x00,0x05,0x04, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0xFF, 0x2A,0x04 },
			{ 0x0F, 0x01, (byte) 0x80,0x05,0x04, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0xDF, (byte) 0xFE,0x04 },
			{ 0x0F, 0x01, 0x00,0x05,0x05, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0xAE, (byte) 0x80,0x04 },
			{ 0x0F, 0x01, (byte) 0x80,0x05,0x05, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0x8E, 0x54,0x04 }};
	}

	public static class PATH {
		public static final String SDCARD_SENSOR_PATH = "/sdcard/skeatest/sensor/";
		public static final String SDCARD_OTA_PATH = "/sdcard/skeatest/ota/";
	}

}
