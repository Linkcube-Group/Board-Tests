package me.linkcube.taku.ui.ota;

import me.linkcube.skeatest.R;
import me.linkcube.taku.AppConst.GameFrame;
import me.linkcube.taku.AppConst.PATH;
import me.linkcube.taku.ui.ota.MemoryBranchLvAdapter.WitchPositionClickListener;
import me.linkcube.taku.ui.ota.OTAManager.BluetoothMsgListener;
import me.linkcube.taku.ui.skeasensor.GraphManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;

import custom.android.app.CustomFragmentActivity;
import custom.android.util.AlertUtils;
import custom.android.util.FormatUtils;

public class ReadMemoryActivity extends CustomFragmentActivity implements
		WitchPositionClickListener,BluetoothMsgListener {

	private String TAG = "ReadMemoryActivity";
	private ListView memoryBranchLv;
	private Button shakeHandBtn,mergeMemoryBtn;
	private MemoryBranchLvAdapter memoryBranchLvAdapter;
	private String getBlueToothString;
	private String[] branchMemoryData = new String[12];
	private int position=-1;
	private TextView inputMsgTv,getMsgTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_memory_activity);

		initView();
	}

	private void initView() {
		memoryBranchLv = (ListView) findViewById(R.id.memoryBranchLv);
		memoryBranchLvAdapter = new MemoryBranchLvAdapter(this,this,branchMemoryData);
		memoryBranchLv.setAdapter(memoryBranchLvAdapter);
		inputMsgTv=(TextView)findViewById(R.id.inputMsgTv);
		getMsgTv=(TextView)findViewById(R.id.getMsgTv);
		shakeHandBtn=(Button)findViewById(R.id.shakeHandBtn);
		shakeHandBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputMsgTv.setText("输入");
				getMsgTv.setText("接收");
				boolean isShakeHand=BTManager.getInstance().sendCommand(GameFrame.SHAKE_HAND_FRAME);
				String blueToothString = FormatUtils.bytesToHexString(GameFrame.SHAKE_HAND_FRAME);
				Log.d(TAG, "SHAKE_HAND_FRAME:"+blueToothString+"--isShakeHand:"+isShakeHand);
				inputMsgTv.setText(blueToothString);
			}
		});
		mergeMemoryBtn = (Button) findViewById(R.id.mergeMemoryBtn);
		mergeMemoryBtn.setVisibility(View.INVISIBLE);
		mergeMemoryBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 合并内容
				StringBuilder sBuilder=new StringBuilder();
				for (int i = 0; i < branchMemoryData.length; i++) {
					sBuilder.append(branchMemoryData[i]);
				}
				Log.d(TAG, "sBuilder:"+sBuilder);
				String fileName=GraphManager.getFileName();
				OTAManager.saveFile(fileName,sBuilder.toString());
				AlertUtils.showToast(ReadMemoryActivity.this, "数据已保存到："+PATH.SDCARD_OTA_PATH+fileName+".txt",Toast.LENGTH_LONG);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		OTAManager.setBluetoothMsgListener(this);
	}

	private Handler getBranchMemoryFrameHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// branchMemoryData[]
			if(getBlueToothString.equals("0f")){
				getMsgTv.setText(getBlueToothString);
			}else{
				branchMemoryData[position]=getBlueToothString;
				memoryBranchLvAdapter.notifyDataSetChanged();
				int i=0;
				for (; i < branchMemoryData.length; i++) {
					if(branchMemoryData[i]==null||branchMemoryData[i].equals("")){
						break;
					}
				}
				if(i==branchMemoryData.length){
					mergeMemoryBtn.setVisibility(View.VISIBLE);
				}
			}
		}

	};

	@Override
	public void getPosition(int position) {
		this.position=position;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		BTManager.getInstance().stopReceiveData();
	}

	@Override
	public void receiveDataUpdateUI(String bluetoothMsg) {
		if(position!=-1){
			getBlueToothString=bluetoothMsg;
			getBranchMemoryFrameHandler.sendEmptyMessage(0);
		}
	}
}
