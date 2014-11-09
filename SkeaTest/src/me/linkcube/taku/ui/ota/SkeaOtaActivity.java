package me.linkcube.taku.ui.ota;

import com.ervinwang.bthelper.BTManager;

import me.linkcube.skeatest.R;
import me.linkcube.taku.AppConst.GameFrame;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import custom.android.app.CustomFragmentActivity;
import custom.android.util.FormatUtils;

public class SkeaOtaActivity extends CustomFragmentActivity implements
		OnClickListener {

	private Button getIntoOtaBtn, nextBtn;
	private TextView inputMsgTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skea_ota_activity);

		getIntoOtaBtn = (Button) findViewById(R.id.getIntoOtaBtn);
		getIntoOtaBtn.setOnClickListener(this);
		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setVisibility(View.GONE);
		nextBtn.setOnClickListener(this);
		inputMsgTv=(TextView)findViewById(R.id.inputMsgTv);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getIntoOtaBtn:
			boolean isGetIntoOta=BTManager.getInstance().sendCommand(GameFrame.INTO_OTA_FRAME);
			String blueToothString = FormatUtils.bytesToHexString(GameFrame.INTO_OTA_FRAME);
			Log.d("SkeaOtaActivity", "INTO_OTA_FRAME:"+blueToothString+"--isGetIntoOta:"+isGetIntoOta);
			inputMsgTv.setText(blueToothString);
			if(isGetIntoOta){
				nextBtn.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.nextBtn:
			startActivity(new Intent(SkeaOtaActivity.this,ShakeHandActivity.class));
			break;
		default:
			break;
		}
	}

}
