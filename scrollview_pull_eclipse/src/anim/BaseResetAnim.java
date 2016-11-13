package anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 继承该类 实现 提供渐变值的inAnim() 方法，完成动画中的 ui变化
 * 
 * @author huyang
 *
 */
public abstract class BaseResetAnim extends Animation {

	protected View animView;
	int startY;
	int endY;

	public BaseResetAnim(View animView, int startY, int endY) {
		this.animView = animView;
		this.startY = startY;
		this.endY = endY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		inAnim(interpolatedTime);
		super.applyTransformation(interpolatedTime, t);
	}

	public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
		int startInt = startValue;
		return (int) (startInt + fraction * (endValue - startInt));
	}

	protected abstract void inAnim(float interpolatedTime);
}
