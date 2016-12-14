package com.bcwcar.android.bencar.base;

import android.util.Log;
import android.widget.TextView;

/**
 * A Simply Logger
 * @author Administrator
 */
public class SLog {
	private static final String LOG_TAG = SLog.class.getSimpleName();
	//	private static volatile boolean mAllowPrintLog = Config.ALLOW_LOG;
	private static volatile boolean mAllowPrintLog = true;

	public static void d(String tag, String msg) {
		//		System.out.println("SLog : mAllowPrintLog = " + mAllowPrintLog);
		//		System.out.println("SLog : Config.ALLOW_LOG = " + Config.ALLOW_LOG);
		if (mAllowPrintLog) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (mAllowPrintLog) {
			Log.e(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (mAllowPrintLog) {
			Log.i(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		//		System.out.println("SLog : mAllowPrintLog = " + mAllowPrintLog);
		//		System.out.println("SLog : Config.ALLOW_LOG = " + Config.ALLOW_LOG);
		if (mAllowPrintLog) {
			Log.v(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (!mAllowPrintLog) {
			return;
		}
		Log.w(tag, msg);
	}

	/**
	 * 效果跟Log.v相同
	 */
	public static final int VERBOSE = Log.VERBOSE;
	/**
	 * 效果跟Log.d相同
	 */
	public static final int DEBUG = Log.DEBUG;
	/**
	 * 效果跟Log.i相同
	 */
	public static final int INFO = Log.INFO;
	/**
	 * 效果跟Log.w相同
	 */
	public static final int WARN = Log.WARN;
	/**
	 * 效果跟Log.e相同
	 */
	public static final int ERROR = Log.ERROR;
	/**
	 * Priority constant for the println method.
	 */
	public static final int ASSERT = Log.ASSERT;

	/**
	 * 用来打印Exception的相关信息
	 * 
	 * @param tag
	 * @param e
	 */
	public static void showExceptionDetails(String tag, Exception e) {
		try {
			if (!mAllowPrintLog) {
				return;
			}
			if(e != null) {
				SLog.e(tag, Log.getStackTraceString(e));
			}
		} catch (Exception exception) {
			// Ignore...
		}
	}

	/**
	 * 将日志输出到TextView中显示
	 * 
	 * @param textView
	 * @param msg
	 */
	public static void  logToView(final TextView textView, final String msg) {
		if (!mAllowPrintLog) {
			return;
		}
		if(textView != null) {
			textView.post(new Runnable() {
				@Override
				public void run() {
					textView.setText(msg);
				}
			});
		} else {
			SLog.e(LOG_TAG, "textView is null...");
		}
	}

	/**
	 * 这个方法可以显示当前类名和方法名 使用打印级别为VERBOSE
	 * 
	 * @param stackTraceInfo
	 *            使用时 请传递这个参数Thread.currentThread().getStackTrace()
	 */
	public static void showClassMethodInfo(StackTraceElement[] stackTraceInfo) {
		showClassMethodMessage(stackTraceInfo, VERBOSE, "");
	}

	/**
	 * 这个方法可以显示当前类名和方法名
	 * 
	 * @param stackTraceInfo
	 *            使用时 请传递这个参数Thread.currentThread().getStackTrace()
	 * @param logLevel
	 *            可以使用此类中的常量引用
	 */
	public static void showClassMethodInfo(StackTraceElement[] stackTraceInfo,
			int logLevel) {
		showClassMethodMessage(stackTraceInfo, logLevel, "");
	}

	/**
	 * 这个方法可以显示当前类名和方法名 以及额外的一些信息
	 * 
	 * @param stackTraceInfo
	 *            使用时 请传递这个参数Thread.currentThread().getStackTrace()
	 * @param logLevel
	 *            可以使用此类中的常量引用
	 * @param extraMessage
	 *            额外打印的信息
	 */
	public static void showClassMethodMessage(
			StackTraceElement[] stackTraceInfo, int logLevel,
			String extraMessage) {
		if (!mAllowPrintLog || stackTraceInfo == null
				|| stackTraceInfo.length < 3) {
			SLog
			.w(LOG_TAG,
					"mAllowPrintLog is false, or stackTraceInfo is null, or stackTraceInfo length is less than 3, return...");
			return;
		}

		// 输出关于类和方法的一些信息
		try {
			StackTraceElement info = stackTraceInfo[2];
			String className = info.getClassName();
			String classNameToLog = className.substring(className
					.lastIndexOf(".") + 1);
			String methodName = info.getMethodName();
			String methodNameToLog = methodName + "()";
			String methodNameWithExtraMsgToLog = methodNameToLog;
			if (extraMessage != null && !"".equalsIgnoreCase(extraMessage)) {
				methodNameWithExtraMsgToLog = methodNameToLog + " : "
						+ extraMessage;
			}
			switch (logLevel) {
			case VERBOSE: {
				SLog.v(classNameToLog, methodNameWithExtraMsgToLog);
				break;
			}
			case DEBUG: {
				SLog.d(classNameToLog, methodNameWithExtraMsgToLog);
				break;
			}
			case INFO: {
				SLog.i(classNameToLog, methodNameWithExtraMsgToLog);
				break;
			}
			case WARN: {
				SLog.w(classNameToLog, methodNameWithExtraMsgToLog);
				break;
			}
			case ERROR: {
				SLog.e(classNameToLog, methodNameWithExtraMsgToLog);
				break;
			}
			default: {
				SLog.w(LOG_TAG,
						"we got an illegal argument, logLevel = " + logLevel);
				break;
			}
			}

		} catch (Exception e) {
			SLog.e(LOG_TAG, e.toString());
		}
	}
	private SLog() {}

}