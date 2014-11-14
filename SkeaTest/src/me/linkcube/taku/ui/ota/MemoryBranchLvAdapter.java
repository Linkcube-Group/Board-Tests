package me.linkcube.taku.ui.ota;

import com.ervinwang.bthelper.BTManager;
import custom.android.util.FormatUtils;
import me.linkcube.skeatest.R;
import me.linkcube.taku.AppConst.GameFrame;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MemoryBranchLvAdapter extends BaseAdapter {
	private String TAG = "MemoryBranchLvAdapter";
	private Context context;
	private WitchPositionClickListener witchPositionClickListener;
	private String[] branchMemoryData = new String[12];

	public MemoryBranchLvAdapter(Context context,
			WitchPositionClickListener witchPositionClickListener,
			String[] branchMemoryData) {
		this.context = context;
		this.witchPositionClickListener = witchPositionClickListener;
		this.branchMemoryData = branchMemoryData;
	}

	@Override
	public int getCount() {
		return 12;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.memory_branch_lv_item, null);
		Button branchItemBtn = (Button) convertView
				.findViewById(R.id.branchItemBtn);
		final CheckBox checkBox = (CheckBox) convertView
				.findViewById(R.id.checkBox);
		TextView memoryContentTv = (TextView) convertView
				.findViewById(R.id.memoryContentTv);
		branchItemBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isGetMemory = BTManager.getInstance().sendCommand(
						GameFrame.READ_MEMORY_FRAMES[position]);
				String blueToothString = FormatUtils
						.bytesToHexString(GameFrame.READ_MEMORY_FRAMES[position]);
				Log.d(TAG, "READ_MEMORY_FRAMES:" + blueToothString
						+ "--isGetMemory:" + isGetMemory);
				witchPositionClickListener.getPosition(position);
			}
		});
		byte[] byteText = new byte[2];
		byteText[0] = GameFrame.READ_MEMORY_FRAMES[position][2];
		byteText[1] = GameFrame.READ_MEMORY_FRAMES[position][3];
		branchItemBtn.setText(FormatUtils.bytesToHexString(byteText));
		if (branchMemoryData[position] != null
				&& !branchMemoryData[position].equals("")) {
			memoryContentTv.setText(branchMemoryData[position]);
			checkBox.setChecked(true);
		}
		return convertView;
	}

	public interface WitchPositionClickListener {
		void getPosition(int position);
	}
}