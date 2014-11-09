package me.linkcube.taku.ui.main;

import me.linkcube.skeatest.R;
import me.linkcube.taku.ui.bt.BTSettingActivity;
import me.linkcube.taku.ui.ota.SkeaOtaActivity;
import me.linkcube.taku.ui.skeasensor.SkeaSensorActivity;
import me.linkcube.taku.utils.TakuHttpClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import custom.android.app.CustomFragmentActivity;

public class SkeaTestActivity extends CustomFragmentActivity implements OnClickListener{

	// 连接蓝牙设备按钮
	private ImageButton connectBtn ;
	// 踏酷
	private Button takuBtn;
	//ota
	private Button otaBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sports_games_fragment);
		
		//TakuHttpClient.initCookie(this);
		
		takuBtn = (Button) findViewById(R.id.takuBtn);
		otaBtn=(Button)findViewById(R.id.otaBtn);
		connectBtn = (ImageButton) findViewById(R.id.connectBtn);
		takuBtn.setOnClickListener(this);
		otaBtn.setOnClickListener(this);
		connectBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.takuBtn:
			startActivity(new Intent(SkeaTestActivity.this,SkeaSensorActivity.class));
			break;
		case R.id.otaBtn:
			startActivity(new Intent(SkeaTestActivity.this,SkeaOtaActivity.class));
			break;
		case R.id.connectBtn:// 连接蓝牙设备
			startActivity(new Intent(SkeaTestActivity.this, BTSettingActivity.class));
			break;
		default:
			break;
		}
	}

}
