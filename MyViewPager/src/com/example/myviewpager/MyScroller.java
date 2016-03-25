package com.example.myviewpager;

import android.content.Context;
import android.os.SystemClock;

/**
 * 弹性滑动的工具类
 * @author Administrator
 *
 */
public class MyScroller {
	private Context context;
	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;
	private long startTime;
	private boolean isFinish;
	private long duration;   //默认滑动时间
	private int currX;   //当前x坐标
	private int currY;   //当前y坐标
	
	
	
	
	public MyScroller(Context context) {
		
		this.context = context;
	}

	/**
	 * 初始化各个变量
	 * @param startX
	 * @param startY
	 * @param distanceX
	 * @param distanceY
	 */
	public void startScroll(int startX,int startY,int distanceX,int distanceY) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		this.startTime = SystemClock.uptimeMillis();
		this.isFinish = false;
		this.duration = 500;
	}
	
	/**
	 * 初始化各个变量，作为外部接口供客户端开始渐变滑动
	 * @param startX
	 * @param startY
	 * @param distanceX
	 * @param distanceY
	 * @param duration
	 */
	public void startScroll(int startX,int startY,int distanceX,int distanceY,long duration) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = distanceX;
		this.distanceX = distanceY;
		this.startTime = SystemClock.uptimeMillis();
		this.isFinish = false;
		this.duration = duration;
	}
	
	/**
	 * 计算当前的运行状态，获得当前应该运行到的位置坐标
	 * @return true:正在运行  false:运行结束
	 */
	public boolean computeScrollOffset() {
		if(isFinish){
			return false;
		}
		//已经滑动时间
		long passTime = SystemClock.uptimeMillis() - startTime;
		if(passTime < duration){
			currX = (int) (startX + passTime*distanceX/duration);
			currY = (int) (startY + passTime*distanceY/duration);
		}else {
			currX = (int) (startX + distanceX);
			currY = (int) (startY + distanceY);
			isFinish = true;
		}
		
		return true;
	}

	public int getCurrX() {
		return currX;
	}

	

	public int getCurrY() {
		return currY;
	}

	
	
	
	
}
