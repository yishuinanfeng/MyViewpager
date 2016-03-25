package com.example.myviewpager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * 自定义ViewPager
 * @author Administrator
 *
 */
public class MyViewPager extends ViewGroup {
	
	private Context context;
	private MyScroller scroller;
//	private Scroller scroller;
	private boolean isFling;
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		scroller = new MyScroller(context);
	//	scroller = new Scroller(context);
		gestureDetector = new GestureDetector(context, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				/**
				 * 根据解析出来的滑动距离调用滑动方法
				 * distanceX为正时，向左移动；为负时，向右移动
				 */
				scrollBy((int) distanceX, 0);
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				//快速滑动的情况下，currId在允许范围内一定发生变化
				isFling	= true;
				if(velocityX>0 && currId>0){
					currId--;
				}else if (velocityX<0 && currId<getChildCount()-1) {
					currId++;
				}
				moveToDest(currId);
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	/**
	 * changed为true，说明布局发生了变化
	 * l/t/r/b 当前viewgroup在父View的位置
	 * 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.layout(getWidth()*i, 0, getWidth()*(i+1), getHeight());
			
		//	view.layout(200*i, i*100, 200*(i+1), 400);   //图片变成参数指定的大小
		}
		
	}
	
	/**
	 * 手势识别器，帮助完成onTouchEvent中手势的解析
	 * 
	 */
	private GestureDetector gestureDetector;
	/**
	 * 当前显示的图片id
	 */
	private int currId;
	/**
	 * down的x坐标 
	 */
	private float firstX;
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { 
		super.onTouchEvent(event);
		
		//event交给gestureDetector执行。在这里是处理滑动事件
		gestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = event.getX();
	System.out.println("down");
			break;
		case MotionEvent.ACTION_MOVE:
	System.out.println("move");

			break;	
		case MotionEvent.ACTION_UP:
	System.out.println("up");
		   //没有快速滑动时，才按位置判断currId
		   if(!isFling){  
			   if(event.getX()-firstX > getWidth()/2){
					currId = currId>0?--currId:0;
				//左滑
				}else if (firstX - event.getX() > getWidth()/2) {
					currId = currId<getChildCount()-1?++currId:(getChildCount()-1);
				}
				//滑动到指定位置，只有up情况下才执行
				moveToDest(currId);  
		   }
			//右滑
			isFling = false;
			
			break;
		default:
			break;
		}
		return true;
	}

	public void moveToDest(int currId) { 
		if(pageChangeListener != null){
			pageChangeListener.moveToDest(currId);
		}
		int distanceX = currId*getWidth() - getScrollX();
		scroller.startScroll(getScrollX(), 0, distanceX, 0);
	//	scroller.startScroll(getScrollX(), 0, distanceX, 0,Math.abs(distanceX));  //距离和时间相同
		//导致onDraw()执行
		invalidate();
	}
	
	/**
	 * 遍历子View进行测量.(若无重写该方法，则调用View的onMeasure()，故只是测量当前ViewGroup)
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	
	/**
	 * 在onDraw()中会被调用，获取当前应该滑动到的位置然后滑动到该位置
	 */
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			int newX = scroller.getCurrX();
			int newY = scroller.getCurrY();
			scrollTo(newX, newY);
			invalidate();
		}
	}
	
	private PageChangeListener pageChangeListener;
	
	
	
	public PageChangeListener getPageChangeListener() {
		return pageChangeListener;
	}

	public void setPageChangeListener(PageChangeListener pageChangeListener) {
		this.pageChangeListener = pageChangeListener;
	}


	/**
	 * 页面滑动的监听器，供外部类的使用
	 * @author Administrator
	 *
	 */
	public interface PageChangeListener{
		void moveToDest(int currId);
	};
	
	
	
}
