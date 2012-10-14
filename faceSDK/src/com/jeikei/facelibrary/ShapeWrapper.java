package com.jeikei.facelibrary;

import android.util.Log;

public class ShapeWrapper {
	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	
	private static ShapeWrapper sWrapper = new ShapeWrapper();
	public static final String TAG = "faceSDK:ShapeWrapper";
	private int frameWidth, frameHeight;
	
	private double [][]shape = new double[2][76];
	private double []faceLocation = new double[2];
	private double faceDistance;
	
	private long timeGap;
	private long startTime = -1;
	
	TimerInterpolator mInterpolator;
	
	private ShapeWrapper()
	{
		mInterpolator = new TimerInterpolator();
		
		for(int i=0; i<76; i++)
		{
			shape[AXIS_X][i] = -1;
			shape[AXIS_Y][i] = -1;
		}
	}
	public static ShapeWrapper getInstance()
	{
		return sWrapper;
	}
	public void initialise(int _frameWidth, int _frameHeight)
	{
		frameWidth = _frameWidth;
		frameHeight = _frameHeight;
		
	}
	public void release()
	{
		mInterpolator.close();
	}
	public void resume()
	{
		if(mInterpolator.getState() == TimerInterpolator.STATE_STOP)
		{
			mInterpolator.init();
		}
	}
	
	public void putShape(double[][] _shape)
	{
		shape = _shape.clone();
		
		//checkTimeGap();
		mInterpolator.putValues(faceLocation[AXIS_X], faceLocation[AXIS_Y], faceDistance);
		
	}
	public int putShape(int x_or_y, int idx, double value)
	{
		if(x_or_y > 2 || x_or_y < 0)	return -1;	//wrong value.
		if(idx >= 76 || idx < 0)		return -1;
		
		shape[x_or_y][idx] = value;
		
	//	Log.i(TAG, "value : " + value);
		if(idx == 75 && x_or_y == AXIS_Y)
		{
			mInterpolator.putValues(getFaceRelativeLocationX(), getFaceRelativeLocationY(), faceDistance);
		}
			//checkTimeGap();
		
		return 1;
	}
	
	public double[][] getShape()
	{
		return shape;
	}
	public double getShape(int x_or_y, int idx)
	{
		if(x_or_y > 2 || x_or_y < 0)	return -1;	//wrong value.
		if(idx >= 76 || idx < 0)		return -1;
		
		return shape[x_or_y][idx];
	}
	
	/**
	 * Return the relative axis of x.
	 * return range is [-1 ~ 1]
	 */
	public double getFaceRelativeLocationX()
	{
		double tmp;
		
		computeFaceLocation();
		
		tmp = faceLocation[AXIS_X] / (double)frameWidth;
		tmp -= 0.5;
		tmp *= 2;
		
		return tmp;
	}
	
	/**
	 * Return the relative axis of y.
	 * return range is [-1 ~ 1]
	 */
	public double getFaceRelativeLocationY()
	{
		double tmp;
		
		computeFaceLocation();
		
		tmp = faceLocation[AXIS_Y] / (double)frameHeight;
		tmp -= 0.5;
		tmp *= -2;
		
		//Log.i(TAG, "frameHeight : " + frameHeight +", faceLocation_Y : "+ faceLocation[AXIS_Y] +  ", tmp : " + tmp);
		
		return tmp;
	}
	
	/**
	 * Return the size of the face.
	 * return ragne is [0 ~ 1]
	 */
	public double getFaceDistance()
	{
		
		if(computeFaceLocation() == -1)
			return -1;
		else return faceDistance;
		
		//Log.i(TAG, "axisX1 : " + shape[AXIS_Y][7] + ", axisX2 : " + shape[AXIS_Y][15] + ", result : " + tmp);
	}

	public double getEstimatedFaceLocationX()
	{
		return mInterpolator.getEstimatedValue(TimerInterpolator.IDX_FACE_LOCATION_X);
	}
	public double getEstimatedFaceLocationY()
	{
		return mInterpolator.getEstimatedValue(TimerInterpolator.IDX_FACE_LOCATION_Y);
	}
	public double getEstimatedFaceDistance()
	{
		return mInterpolator.getEstimatedValue(TimerInterpolator.IDX_FACE_DISTANCE);
	}
	
	public double getTimeGap()
	{
		//Log.i(TAG, "timeGap = " + timeGap );
		//return timeGap;
		return mInterpolator.getTimeGap();
	}
	
	private int computeFaceLocation()
	{
		//Log.i(TAG, "timeGap = " + computeTimeRatio() );
		 //computeTimeRatio();
		 
		if(shape[AXIS_X][67] == -1 || shape[AXIS_Y][67] == -1)	return -1;
		
		faceLocation[AXIS_X] = (double)shape[AXIS_X][67];
		faceLocation[AXIS_Y] = (double)shape[AXIS_Y][67];
		
		//Log.i(TAG, "shape_y : " + faceLocation[AXIS_Y]);
		
		faceDistance = ( (shape[AXIS_X][14]-shape[AXIS_X][0]) / frameWidth ) * 0.5 + 
				( (shape[AXIS_Y][7]-shape[AXIS_Y][15]) / frameWidth ) * 0.5;
		
		return 1;
	}
}
