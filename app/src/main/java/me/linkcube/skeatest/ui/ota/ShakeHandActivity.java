package me.linkcube.skeatest.ui.ota;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ervinwang.bthelper.BTManager;

import custom.android.app.CustomFragmentActivity;
import custom.android.util.FormatUtils;
import me.linkcube.skeatest.AppConst.GameFrame;
import me.linkcube.skeatest.R;
import me.linkcube.skeatest.ui.ota.OTAManager.BluetoothMsgListener;

public class ShakeHandActivity extends CustomFragmentActivity implements
		OnClickListener,BluetoothMsgListener {

	private String TAG="ShakeHandActivity";
	private Button shakeHandBtn, preBtn, nextBtn;
	private TextView getMsgTv;
	private String getBlutToothString;
	private TextView inputMsgTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shake_hand_activity);

		initView();
		OTAManager.startReceiveData();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		OTAManager.setBluetoothMsgListener(this);
	}

	private void initView() {
		shakeHandBtn = (Button) findViewById(R.id.shakeHandBtn);
		preBtn = (Button) findViewById(R.id.preBtn);
		nextBtn = (Button) findViewById(R.id.nextBtn);
		//nextBtn.setVisibility(View.GONE);
		getMsgTv = (TextView) findViewById(R.id.getMsgTv);
		inputMsgTv=(TextView)findViewById(R.id.inputMsgTv);
		
		shakeHandBtn.setOnClickListener(this);
		preBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
	}
	
	private Handler getShakeHandFrameHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getMsgTv.setText(getBlutToothString);
			nextBtn.setVisibility(View.VISIBLE);
		}
		
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shakeHandBtn:
			inputMsgTv.setText("输入:");
			getMsgTv.setText("等待消息传入...");
			boolean isShakeHand=BTManager.getInstance().sendCommand(GameFrame.SHAKE_HAND_FRAME);
			String blueToothString = FormatUtils.bytesToHexString(GameFrame.SHAKE_HAND_FRAME);
			Log.d(TAG, "SHAKE_HAND_FRAME:"+blueToothString+"--isShakeHand:"+isShakeHand);
			inputMsgTv.setText(blueToothString);
			break;
		case R.id.preBtn:
			finish();
			break;
		case R.id.nextBtn:
			startActivity(new Intent(ShakeHandActivity.this,ReadBLVertionNumberActivity.class));
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
		getBlutToothString=bluetoothMsg;
		getShakeHandFrameHandler.sendEmptyMessage(0);
	}
}
