package com.example.scrollview_pull;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ScrollView;

import utils.MyUtils;

//融合版 
public class MySelfScrollView_s extends ScrollView {
	float downY;
	View animView;
	int animInitHeight;

	// 拉伸的最大高度，默认 屏幕宽度
	int animMaxHeight;

	public MySelfScrollView_s(Context context) {
		super(context);

	}

	public MySelfScrollView_s(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOverScrollMode(OVER_SCROLL_NEVER);

		// 初始拉伸最大高度
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) getContext()).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		animMaxHeight = dm.widthPixels;
	}

	public void setAnimView(View v) {
		animView = v;
		animView.measure(0, 0);
		animInitHeight = animView.getMeasuredHeight();
		//animInitHeight = MyUtils.dip2px(getContext(),200);
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
		ResetAnimtion pa = new ResetAnimtion(animView, animView.getHeight(),
				animInitHeight);
		// 每speed px, 花费speed毫秒 移动；觉得不爽可以改成固定值
		int speed = 300;
		int time = Math.abs(animView.getHeight() - animInitHeight) / speed
				* speed + speed;
		pa.setDuration(time);
		pa.setInterpolator(new OvershootInterpolator());
		animView.startAnimation(pa);
	}

	class ResetAnimtion extends Animation {
		protected View animView;
		int startY;
		int endY;

		public ResetAnimtion(View animView, int startY, int endY) {
			this.animView = animView;
			this.startY = startY;
			this.endY = endY;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			int cY = evaluate(interpolatedTime, startY, endY);
			animView.getLayoutParams().height = cY;
			animView.requestLayout();
			super.applyTransformation(interpolatedTime, t);
		}

		public Integer evaluate(float fraction, Integer startValue,
				Integer endValue) {
			int startInt = startValue;
			return (int) (startInt + fraction * (endValue - startInt));
		}
	}

	public void setInitHeight() {
		animView.getLayoutParams().height = animInitHeight;
		animView.requestLayout();
	}
}
