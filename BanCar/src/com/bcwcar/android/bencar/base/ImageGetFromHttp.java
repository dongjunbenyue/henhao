package com.bcwcar.android.bencar.base;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class ImageGetFromHttp {
    private static final String LOG_TAG = "ImageGetFromHttp";
                                                           
    public static void downloadBitmap(final String imageUrl,final Handler handler) {

    	new Thread(){
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			//httpGet连接对象  
    			HttpGet httpRequest = new HttpGet(imageUrl);  
    			//取得HttpClient 对象  
    			HttpClient httpclient = new DefaultHttpClient();  
    			try {  
    				//请求httpClient ，取得HttpRestponse  
    				HttpResponse httpResponse = httpclient.execute(httpRequest);  
    				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
    					//取得相关信息 取得HttpEntiy  
    					HttpEntity httpEntity = httpResponse.getEntity();  
    					//获得一个输入流  
    					InputStream is = httpEntity.getContent();  
    					Bitmap bitmap = BitmapFactory.decodeStream(is);  
    					is.close();  
    					if (bitmap!=null) {
    						Message msg = handler.obtainMessage(0, bitmap);
    						handler.sendMessage(msg);
						}
    				}  
    				
    			} catch (Exception e) {  
    				// TODO Auto-generated catch block  
    				e.printStackTrace();  
    			} 
    			super.run();
    		}
    	}.start();
	
    }
                                                       
    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
               
        
        /*
         *Android对于InputStream流有个小bug在慢速网络的情况下可能产生中断，
         *可以考虑重写FilterInputStream处理skip方法来解决这个bug。
         * BitmapFactory类的decodeStream方法在网络超时或较慢的时候无法获取完整的数据
         * ，这里我 们通过继承FilterInputStream类的skip方法来强制实现flush流中的数据，主要原理就是检查是否到文件末端
         * ，告诉http类是否继续。 
         */
        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}
