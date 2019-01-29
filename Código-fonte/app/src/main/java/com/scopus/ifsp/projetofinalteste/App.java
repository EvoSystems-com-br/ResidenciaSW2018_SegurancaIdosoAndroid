package com.scopus.ifsp.projetofinalteste;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.scopus.ifsp.projetofinalteste.helper.DatabaseHelper;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;

/**
 * @author Denis Magno
 */
public class App extends Application {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");

        this.daoSession = DatabaseHelper.getInstance(this).daoSession;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e(TAG, "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG, "onTrimMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG, "onTerminate");
    }
}
