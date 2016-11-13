package anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 通过设置 padding值来改变ui  scrollview 里面不适用
 * @author huyang
 *
 */
public class PaddBottumResetAnimaiton extends BaseResetAnim {

	public PaddBottumResetAnimaiton(View v, int sY, int eY) {
		super(v, sY, eY);

	}

	@Override
	protected void inAnim(float interpolatedTime) {
		int cY = evaluate(interpolatedTime, startY, endY);
		animView.setPadding(0, 0, 0, cY);
	}

}
