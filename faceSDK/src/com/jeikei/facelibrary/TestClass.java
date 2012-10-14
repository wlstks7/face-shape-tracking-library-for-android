package com.jeikei.facelibrary;

import android.util.Log;

public class TestClass {

	private ShapeWrapper sWrapper;
	private String TAG = "faceSDK:TestClass";
	public TestClass()
	{
		sWrapper = ShapeWrapper.getInstance();
	}
	
	public void printLog()
	{
		Log.i(TAG, "location : " + sWrapper.getFaceRelativeLocationX());
	}
}
