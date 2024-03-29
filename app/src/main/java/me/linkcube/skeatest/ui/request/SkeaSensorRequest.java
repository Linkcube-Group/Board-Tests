package me.linkcube.skeatest.ui.request;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import me.linkcube.skeatest.AppConst.ErrorFlag;
import me.linkcube.skeatest.AppConst.ResponseKey;
import me.linkcube.skeatest.utils.HttpResponseListener;
import me.linkcube.skeatest.utils.TakuHttpClient;

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
