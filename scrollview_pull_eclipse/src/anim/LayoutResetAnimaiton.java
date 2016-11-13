package anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class LayoutResetAnimaiton extends BaseResetAnim {

	public LayoutResetAnimaiton(View v, int sY, int eY) {
		super(v, sY, eY);
	}

	@Override
	protected void inAnim(float interpolatedTime) {
		int cY = evaluate(interpolatedTime, startY, endY);
		animView.getLayoutParams().height = cY;
		animView.requestLayout();
	}

}
