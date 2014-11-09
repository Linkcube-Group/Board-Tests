package me.linkcube.taku.ui.request;

import me.linkcube.taku.AppConst.ErrorFlag;
import me.linkcube.taku.AppConst.ResponseKey;
import me.linkcube.taku.utils.HttpResponseListener;
import me.linkcube.taku.utils.TakuHttpClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SkeaSensorRequest {

	public static void uploadSkeaData(RequestParams params,
			final HttpResponseListener httpResponseListener) {
		TakuHttpClient.post("", params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.d("SkeaData", "statusCode:" + statusCode + "---response:"
						+ response.toString());
				try {
					if (statusCode == 200) {
						if (response.getBoolean(ResponseKey.STATUS)) {
							httpResponseListener.responseSuccess();
						} else {
							httpResponseListener.responseFailed(ErrorFlag.OTHER_ERROR);
						}
					} else {
						httpResponseListener.responseFailed(ErrorFlag.NETWORK_ERROR);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
