package utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static void show(Context c,String s){
		Toast.makeText(c, s, 0).show();
	}
}
