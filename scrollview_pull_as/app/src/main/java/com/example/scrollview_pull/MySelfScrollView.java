package com.example.scrollview_pull;

import utils.MyUtils;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import anim.AnimUtils;

//看懂应该 没啥难度吧 ，也就是些改变 view 高度的逻辑，和返回动画 最重要的是 思路： 
//1 在scrollview 里面的 布局 用一个支持两层显示的viewgroup 当处理View Framlayout||RelativeLayout xml高度设为 wrap_content，自适应 
//2 背景层用ImageView 设置属性 android:scaleType="centerCrop" 
//3 数据ui层 ViewGroup 即为animView
//	重新设置背景iv 需调用 setInitHeight()方法 animView初始化高度

public class MySelfScrollView extends ScrollView {
	float downY;
	View animView;
	int animInitHeight;

	// 拉伸的最大高度，默认 屏幕宽度
	int animMaxHeight = MyUtils.getScreenWidth((Activity) getContext());

	public MySelfScrollView(Context context) {
		super(context);

	}

	public MySelfScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOverScrollMode(OVER_SCROLL_NEVER);
	}

	public void setAnimView(View v) {
		animView = v;
		animView.measure(0, 0);
		animInitHeight = animView.getMeasuredHeight();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float cY = ev.getY();
			float deltaY = (cY - downY) / 4;
			downY = cY;
			if (deltaY > 0 && animView.getHeight() >= animMaxHeight) {
				break;
			}
			if (getScrollY() == 0) {
				animView.getLayoutParams().height = animView.getHeight()
						+ (int) deltaY;
				animView.requestLayout();
				if (animView.getHeight() >= animInitHeight) {
					return true;
				}
			}

			break;
		case MotionEvent.ACTION_UP:
			if (animView.getHeight() != animInitHeight) {
				startResetAnim();
			}
			break;

		}
		return super.onTouchEvent(ev);
	}

	private void startResetAnim() {
		AnimUtils.startPullIsLayoutAnim(animView, animView.getHeight(),
				animInitHeight);
	}

	/**
	 * 背景iv 重新设置 图片后 需要调用此方法
	 */
	public void setInitHeight() {
		animView.getLayoutParams().height = animInitHeight;
		animView.requestLayout();
	}
}
