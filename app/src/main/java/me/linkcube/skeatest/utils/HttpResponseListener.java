package me.linkcube.skeatest.utils;

public interface HttpResponseListener {

	void responseSuccess();
	void responseFailed(int flag);
}
