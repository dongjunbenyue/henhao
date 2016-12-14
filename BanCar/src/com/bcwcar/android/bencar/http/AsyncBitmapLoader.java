package com.bcwcar.android.bencar.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncBitmapLoader {
	/**
	 * 内存图片软引用缓冲
	 */
	private HashMap<String, SoftReference<Bitmap>> imageCache = null;
	private String base_url ="/mnt/sdcard/";

	public AsyncBitmapLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}
	public Bitmap loadBitmap(final ImageView imageView, final String imageURL, final ImageCallBack imageCallBack) {
		try {
			System.out.println(imageURL+"-------------------");
		// 在内存缓存中，则返回Bitmap对象
		if (imageCache.containsKey(imageURL)) {
			
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		} else {
			/**
			 * 加上一个对本地缓存的查找
			 */


			String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
			File cacheDir = new File(base_url);
			File[] cacheFiles = cacheDir.listFiles();
			int i = 0;
			for (; i < cacheFiles.length; i++) {
				if (bitmapName.equals(cacheFiles[i].getName())) {
					System.out.println(bitmapName+"==================="+cacheFiles[i].getName());
					return BitmapFactory.decodeFile(base_url + bitmapName);
				}
			}
			if (i < cacheFiles.length) {
				if (BitmapFactory.decodeFile(base_url + bitmapName)!=null) {
					return BitmapFactory.decodeFile(base_url + bitmapName);
				}
			}
		}
		


		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				imageCallBack.imageLoad(imageView, (Bitmap) msg.obj);
			}
		};
		// 如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
		new Thread() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				try {
					
				// TODO Auto-generated method stub


				InputStream bitmapIs = HttpUtils.getStreamFromURL(imageURL);
				Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
				imageCache.put(imageURL, new SoftReference<Bitmap>(bitmap));
				Message msg = handler.obtainMessage(0, bitmap);
				handler.sendMessage(msg);
				File dir = new File(base_url);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File bitmapFile = new File(base_url + imageURL.substring(imageURL.lastIndexOf("/") + 1));
				if (!bitmapFile.exists()) {
				
						bitmapFile.createNewFile();
					
				}
				FileOutputStream fos;
					fos = new FileOutputStream(bitmapFile);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
				}
				 catch (Exception e) {
					// TODO Auto-generated catch block
					 
					e.printStackTrace();
					return;
				}
			}
		}.start();
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public Bitmap loadBitmap001(final String imageURL, final ImageCallBack001 imageCallBack) {
		try {
			
		// 在内存缓存中，则返回Bitmap对象
		if (imageCache.containsKey(imageURL)) {
			
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		} else {
			/**
			 * 加上一个对本地缓存的查找
			 */


			String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
			File cacheDir = new File(base_url);
			File[] cacheFiles = cacheDir.listFiles();
			int i = 0;
			for (; i < cacheFiles.length; i++) {
				if (bitmapName.equals(cacheFiles[i].getName())) {
					System.out.println(bitmapName+"==================="+cacheFiles[i].getName());
					return BitmapFactory.decodeFile(base_url + bitmapName);
				}
			}
			if (i < cacheFiles.length) {
				if (BitmapFactory.decodeFile(base_url + bitmapName)!=null) {
					return BitmapFactory.decodeFile(base_url + bitmapName);
				}
			}
		}
		


		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				imageCallBack.imageLoad((Bitmap) msg.obj);
			}
		};
		// 如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
		new Thread() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				try {
					
				// TODO Auto-generated method stub


				InputStream bitmapIs = HttpUtils.getStreamFromURL(imageURL);
				Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
				imageCache.put(imageURL, new SoftReference<Bitmap>(bitmap));
				Message msg = handler.obtainMessage(0, bitmap);
				handler.sendMessage(msg);
				File dir = new File(base_url);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File bitmapFile = new File(base_url + imageURL.substring(imageURL.lastIndexOf("/") + 1));
				if (!bitmapFile.exists()) {
				
						bitmapFile.createNewFile();
					
				}
				FileOutputStream fos;
					fos = new FileOutputStream(bitmapFile);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
				}
				 catch (Exception e) {
					// TODO Auto-generated catch block
					 
					e.printStackTrace();
					return;
				}
			}
		}.start();
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		return null;
	}

	public interface ImageCallBack {
		public void imageLoad(ImageView imageView, Bitmap bitmap);
	}
	public interface ImageCallBack001 {
		public void imageLoad(Bitmap bitmap);
	}

}
