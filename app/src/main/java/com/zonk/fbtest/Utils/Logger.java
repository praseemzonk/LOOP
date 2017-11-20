package com.zonk.fbtest.Utils;

import android.content.Context;
import android.util.Log;

import com.zonk.fbtest.Fbtest;
import com.zonk.fbtest.R;


public class Logger {

    public static String TAG = "BoozkeyBusiness";

    private static Logger instance;
    boolean debugEnabled;
    private Fbtest application;

    private Logger(Fbtest application) {
        super();
        this.setApplication(application);
        debugEnabled = this.application.getResources().getBoolean(R.bool.isDebugEnabled);
    }

    public static synchronized Logger init(Fbtest application) {
        if (null == instance) {
            instance = new Logger(application);
        }
        return instance;
    }

    public void debug(String msg) {
        if (debugEnabled && msg != null) {
            Log.d(TAG, msg);
        }
    }

    public void debug(String msg, Throwable t) {
        if (debugEnabled && msg != null) {
            Log.d(TAG, msg, t);
        }
    }

    public void debug(Throwable t) {
        if (debugEnabled) {
            Log.d(TAG, "Exception:", t);
        }
    }

    public void debug(String tag, String msg) {
        if (debugEnabled && msg != null) {
            Log.d(tag, msg);
        }
    }

    public void warn(String msg) {
        Log.w(TAG, msg);
    }

    public void info(String msg) {
        Log.i(TAG, msg);
    }

    public void error(String msg) {
        Log.e(TAG, msg);
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    /**
     * @return the application
     */
    public Context getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(Fbtest application) {
        this.application = application;
    }

}
