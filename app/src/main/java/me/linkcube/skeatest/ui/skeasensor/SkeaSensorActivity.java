package me.linkcube.skeatest.ui.skeasensor;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.IReceiveData;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.LimitLine;
import com.github.mikephil.charting.utils.LimitLine.LimitLabelPosition;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import custom.android.app.CustomFragmentActivity;
import custom.android.util.AlertUtils;
import me.linkcube.skeatest.AppConst.GameFrame;
import me.linkcube.skeatest.AppConst.PATH;
import me.linkcube.skeatest.AppConst.ParamKey;
import me.linkcube.skeatest.R;
import me.linkcube.skeatest.ui.request.SkeaSensorRequest;
import me.linkcube.skeatest.utils.HttpResponseListener;

public class SkeaSensorActivity extends CustomFragmentActivity {

	private LineChart mChart;
	private TextView dataLevelTv;
	private PowerManager.WakeLock mWakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph_test_activity);
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		
		GraphManager.init(this);
		
		mChart = (LineChart) findViewById(R.id.chart1);
		dataLevelTv=(TextView)findViewById(R.id.dataLevelTv);

		// mChart.setUnit(" $");
		mChart.setDrawUnitsInChart(true);

		mChart.setStartAtZero(true);

		mChart.setDrawYValues(false);

		mChart.setDrawBorder(true);
		mChart.setBorderPositions(new BorderPosition[] { BorderPosition.BOTTOM });

		mChart.setDescription("");
		mChart.setNoDataTextDescription("You need to provide data for the chart.");

		mChart.setHighlightEnabled(true);

		mChart.setHighlightIndicatorEnabled(false);

		// add data
		//timer = new Timer();
		setData(120, 255);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWakeLock.acquire();
	}

	private ArrayList<Entry> yVals = new ArrayList<Entry>();
	private ArrayList<String> xVals = new ArrayList<String>();
	private LineDataSet set1;
	//private Timer timer;
	private int countTemp = 0;
	private boolean isStart = false;

	LimitLine ll1 = new LimitLine(100f);

	private void setData(final int count, float range) {

		for (int i = 0; i < count; i++) {
			xVals.add((i) + "");
		}

		BTManager.getInstance().startReceiveData(new IReceiveData() {

			@Override
			public void receiveData(int bytes, byte[] buffer) {

				byte[] buf_data = new byte[bytes];
				for (int i = 0; i < bytes; i++) {
					buf_data[i] = buffer[i];
				}
				if (buf_data[0] == GameFrame.PRESS_FRAME[0]
						&& buf_data[1] == GameFrame.PRESS_FRAME[1]) {
					if (!isStart) {
						set1 = new LineDataSet(yVals, "DataSet 1");

						set1.setColor(Color.BLACK);
						set1.setCircleColor(Color.BLACK);
						set1.setLineWidth(0f);
						set1.setCircleSize(1f);
						set1.setFillAlpha(0);
						set1.setFillColor(Color.BLACK);

						ll1.setLineWidth(4f);
						ll1.enableDashedLine(10f, 10f, 0f);
						ll1.setDrawValue(true);
						ll1.setLabelPosition(LimitLabelPosition.RIGHT);

						isStart = true;
						handlerInit.sendEmptyMessage(0);
					}
					if (countTemp >= 120) {
						for (int j = 1; j < yVals.size(); j++) {
							yVals.get(j).setXIndex(j - 1);
						}
						yVals.remove(0);
						yVals.add(new Entry(byteToInt(buf_data[3]), 120));
						xVals.remove(0);
						sBuilder.append(countTemp + "---"
								+ GraphManager.getMillSecond() + "---"
								+ getData() + ",\n");
						countTemp++;
						xVals.add(countTemp + "");
						final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
						dataSets.add(set1);
						LineData data = new LineData(xVals, dataSets);

						data.addLimitLine(ll1);

						mChart.setData(data);
						
						Message msg=new Message();
						msg.what=byteToInt(buf_data[2]);
						handler.sendMessage(msg);
					} else {
						yVals.add(new Entry(byteToInt(buf_data[3]), countTemp));
						System.out.println("val:" + byteToInt(buf_data[3])
								+ "--countTemp:" + countTemp);
						sBuilder.append(countTemp + "---"
								+ GraphManager.getMillSecond() + "---"
								+ getData() + ",\n");
						countTemp++;
						final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
						dataSets.add(set1);
						LineData data = new LineData(xVals, dataSets);

						data.addLimitLine(ll1);

						mChart.setData(data);
						
						Message msg=new Message();
						msg.what=byteToInt(buf_data[2]);
						handler.sendMessage(msg);
					}
				}
			}
		});

//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				if (!isStart) {
//					set1 = new LineDataSet(yVals, "DataSet 1");
//
//					set1.setColor(Color.BLACK);
//					set1.setCircleColor(Color.BLACK);
//					set1.setLineWidth(0f);
//					set1.setCircleSize(1f);
//					set1.setFillAlpha(0);
//					set1.setFillColor(Color.BLACK);
//
//					ll1.setLineWidth(4f);
//					ll1.enableDashedLine(10f, 10f, 0f);
//					ll1.setDrawValue(true);
//					ll1.setLabelPosition(LimitLabelPosition.RIGHT);
//
//					isStart = true;
//					handlerInit.sendEmptyMessage(0);
//				}
//				if (countTemp >= 120) {
//					for (int j = 1; j < yVals.size(); j++) {
//						yVals.get(j).setXIndex(j - 1);
//					}
//					yVals.remove(0);
//					yVals.add(new Entry(getData(), 120));
//					xVals.remove(0);
//					countTemp++;
//					xVals.add(countTemp + "");
//					final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//					dataSets.add(set1);
//					LineData data = new LineData(xVals, dataSets);
//
//					data.addLimitLine(ll1);
//
//					mChart.setData(data);
//					handler.sendEmptyMessage(0);
//				} else {
//					yVals.add(new Entry(getData(), countTemp));
//					System.out.println("val:" + getData() + "--countTemp:"
//							+ countTemp);
//					
//					sBuilder.append(countTemp+"---"+ GraphManager.getMillSecond()+"---"+getData()+",\n");
//					countTemp++;
//					final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//					dataSets.add(set1);
//					LineData data = new LineData(xVals, dataSets);
//
//					data.addLimitLine(ll1);
//
//					mChart.setData(data);
//					handler.sendEmptyMessage(0);
//				}
//
//			}
//		}, 0, 100);
	}
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mChart.invalidate();
			dataLevelTv.setText("level:"+msg.what);
		}

	};

	private Handler handlerInit = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mChart.animateX(2500);
			//Legend l = mChart.getLegend();
			//l.setForm(LegendForm.LINE);
		}

	};
	
	

	@Override
	protected void onPause() {
		super.onPause();
		mWakeLock.release();
	}

	private StringBuilder sBuilder=new StringBuilder();
	private RequestParams params;
	private File skeaData;
	@Override
	protected void onStop() {
		super.onStop();
		//timer.cancel();
		//timer=null;
		String fileName=GraphManager.getFileName();
		GraphManager.saveFile(fileName,sBuilder.toString());
		AlertUtils.showToast(SkeaSensorActivity.this, "数据已保存到："+PATH.SDCARD_SENSOR_PATH+fileName+".txt",Toast.LENGTH_LONG);
		skeaData=new File(PATH.SDCARD_SENSOR_PATH+fileName+".txt");
		params = new RequestParams();
		try {
			params.put(ParamKey.IMEI, GraphManager.getIMEI());
			params.put(ParamKey.FIlE, skeaData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SkeaSensorRequest.uploadSkeaData(params, new HttpResponseListener() {
			
			@Override
			public void responseSuccess() {
				AlertUtils.showToast(SkeaSensorActivity.this,
						"上传文件成功！");
			}
			
			@Override
			public void responseFailed(int flag) {
				
			}
		});
	}

	public static int byteToInt(byte mByte) {

		int addr = mByte & 0xFF;

		return addr;
	}

	public static float getData() {
		float val = (float) (Math.random() * 100) + 3;
		return val;
	}
}
