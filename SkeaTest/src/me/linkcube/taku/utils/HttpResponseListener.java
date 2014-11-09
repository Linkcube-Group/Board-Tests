package me.linkcube.taku.utils;

public interface HttpResponseListener {

	void responseSuccess();
	void responseFailed(int flag);
}
