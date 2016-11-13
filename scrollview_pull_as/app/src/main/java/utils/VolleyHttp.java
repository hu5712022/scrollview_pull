package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyHttp {
	private static RequestQueue rq;
	public static String URL = "";
	private static Request<String> request;
	private static ImageRequest ir;

	/**
	 * 获取 bitmap 请求
	 */
	public static void HttpGetBitmap(Context c, String url,
			final doGetBitmapListener ok, final doErrorListener no) {
		if (rq == null) {
			rq = Volley.newRequestQueue(c);
		}
		ir = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				ok.doGet(response);
			}
		}, 0, 0, Config.ARGB_8888, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (no != null)
					no.doError(error);
			}
		});
		rq.add(ir);
		rq.start();

	}

	/**
	 * get 请求-----------------------------------
	 */
	public static void HttpGet(final Context c, String url,
			final doGetListener doGet) {

		HttpGet(c, url, doGet, null);
	}

	public static void HttpGet(final Context c, String url,
			final doGetListener doGet, final doErrorListener doError) {

		if (rq == null) {
			rq = Volley.newRequestQueue(c);
		}
		request = new StringRequest(Method.GET, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				doGet.doGet(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (doError == null) {
					Toast.makeText(c, "net ..", 0).show();
				} else {
					doError.doError(error);
				}
			}
		});
		rq.add(request);
		rq.start();

	}

	/**
	 * get 请求-----------------------------------
	 */

	/**
	 * post 请求-----------------------------------
	 */

	public static void HttpPost(final Context c, String url,
			final doGetListener doGet, final HashMap<String, String> hm) {
		HttpPost(c, url, doGet, null, hm);
	}

	public static void HttpPost(final Context c, String url,
			final doGetListener doGet, final doErrorListener doError,
			final HashMap<String, String> hm) {

		if (rq == null) {
			rq = Volley.newRequestQueue(c);
		}
		System.out.println("post" + url);
		request = new StringRequest(Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {

				doGet.doGet(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (doError == null) {
					// System.out.println(error.getMessage());
					Toast.makeText(c, "网络错误", 0).show();
				} else {
					doError.doError(error);
				}
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				return hm;
			}
		};
		rq.add(request);
		rq.start();
		
	}

	/**
	 * post 请求-----------------------------------
	 */

	/**
	 * 下载图片成功接口
	 */
	public interface doGetBitmapListener {
		public void doGet(Bitmap response);
	}

	/**
	 * 访问成功接口
	 */
	public interface doGetListener {
		public void doGet(String response);
	}

	/**
	 * 访问失败接口
	 */
	public interface doErrorListener {
		public void doError(VolleyError error);
	}

	/**
	 * 访问全地址 请求
	 */
	public static void HttpAllUrlGet(final Context c, String url,
			final doGetListener doGet, final doErrorListener doError) {

		if (rq == null) {
			rq = Volley.newRequestQueue(c);
		}

		System.out.println("get" + url);
		request = new StringRequest(Method.GET, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				doGet.doGet(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (doError == null) {
					Toast.makeText(c, "网络错误", 0).show();
				} else {
					doError.doError(error);
				}
				// System.out.println(error.getMessage());
			}
		});
		rq.add(request);
		rq.start();

	}
}
