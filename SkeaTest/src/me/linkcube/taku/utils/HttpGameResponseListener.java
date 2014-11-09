package me.linkcube.taku.utils;

public interface HttpGameResponseListener {

	void responseSuccess(Object object);
	
	void responseFailed(int flag);
}
