package com.zs.marketmobile.listener;


public interface IOnDownLoadListener {
	public void onProgress(int progress);

	public void onfailed(String msg);
}