package me.linkcube.taku.ui.ota;

import java.util.Timer;
import java.util.TimerTask;

import me.linkcube.skeatest.R;
import me.linkcube.taku.AppConst.GameFrame;
import me.linkcube.taku.ui.ota.OTAManager.BluetoothMsgListener;

import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import custom.android.app.CustomFragmentActivity;
import custom.android.util.FormatUtils;

public class ReadBLVertionNumberActivity extends CustomFragmentActivity
		implements OnClickListener, BluetoothMsgListener {

	private String TAG = "ReadBLVertionNumberActivity";
	private Button readBLVersionBtn, preBtn, nextBtn;
	private TextView getMsgTv;
	private String getBlutToothString;
	private TextView inputMsgTv, shakeHandTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_bl_vertion_number_activity);

		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		OTAManager.setBluetoothMsgListener(this);
	}

	private void initView() {
		readBLVersionBtn = (Button) findViewById(R.id.readBLVersionBtn);
		preBtn = (Button) findViewById(R.id.preBtn);
		nextBtn = (Button) findViewById(R.id.nextBtn);
		// nextBtn.setVisibility(View.GONE);
		getMsgTv = (TextView) findViewById(R.id.getMsgTv);
		inputMsgTv = (TextView) findViewById(R.id.inputMsgTv);
		shakeHandTv = (TextView) findViewById(R.id.shakeHandTv);

		readBLVersionBtn.setOnClickListener(this);
		preBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
	}

	private Handler readBLVersionFrameHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (getBlutToothString.equals("0f")) {
				shakeHandTv.setText("握手成功:" + getBlutToothString);
				boolean isReadBL = BTManager.getInstance().sendCommand(
						GameFrame.READ_BL_FRAME);
				String blueToothString = FormatUtils
						.bytesToHexString(GameFrame.READ_BL_FRAME);
				Log.d(TAG, "READ_BL_FRAME:" + blueToothString + "--isReadBL:"
						+ isReadBL);
				inputMsgTv.setText(blueToothString);
			} else {
				getMsgTv.setText(getBlutToothString);
				nextBtn.setVisibility(View.VISIBLE);
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.readBLVersionBtn:
			shakeHandTv.setText("握手");
			inputMsgTv.setText("");
			getMsgTv.setText("等待获取BL版本号...");
			boolean isShakeHand = BTManager.getInstance().sendCommand(
					GameFrame.SHAKE_HAND_FRAME);
			String blueToothString = FormatUtils
					.bytesToHexString(GameFrame.SHAKE_HAND_FRAME);
			Log.d(TAG, "SHAKE_HAND_FRAME:" + blueToothString + "--isShakeHand:"
					+ isShakeHand);
			break;
		case R.id.preBtn:
			finish();
			break;
		case R.id.nextBtn:
			startActivity(new Intent(ReadBLVertionNumberActivity.this,
					ReadMemoryActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void receiveDataUpdateUI(String bluetoothMsg) {
		getBlutToothString = bluetoothMsg;
		readBLVersionFrameHandler.sendEmptyMessage(0);
	}
}
