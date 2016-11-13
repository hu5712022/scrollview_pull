package anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

public class AnimUtils {

	public static void startPullIsLayoutAnim(final View animView,
			final int startY, final int endY) {
		LayoutResetAnimaiton pa = new LayoutResetAnimaiton(animView,
				animView.getHeight(), endY);
		// 每speed px, 花费speed毫秒 移动；觉得不爽可以改成固定值
		int speed = 300;
		int time = Math.abs(animView.getHeight() - endY) / speed * speed
				+ speed;
		pa.setDuration(time);
		pa.setInterpolator(new OvershootInterpolator());
		animView.startAnimation(pa);
	}

}
