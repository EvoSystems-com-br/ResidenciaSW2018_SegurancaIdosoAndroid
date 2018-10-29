package br.edu.ifsp.monitoramento.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author Denis Magno
 */
public class Service {
    public static boolean isServiceRunning(Context context, String servicoClassName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).service.getClassName().compareTo(servicoClassName) == 0) {
                return true;
            }
        }

        return false;
    }
}