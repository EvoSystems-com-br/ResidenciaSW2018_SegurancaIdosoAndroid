package com.scopus.ifsp.projetofinalteste.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmScheduler {
    private static final int NOTI_PRIMARY1 = 100;

    public void setAlarm(Context context, long alarmTime, PendingIntent operation) {

        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, operation);

    }

    public void setRepeatAlarm(Context context, long alarmTime, long RepeatTime, PendingIntent operation){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, RepeatTime, operation);
    }

    public void cancelAlarm(Context context,PendingIntent operation,int idAlarme){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTI_PRIMARY1 + idAlarme);

        manager.cancel(operation);
    }

    public void cancelOnlyAlarm(Context context,PendingIntent operation,int idAlarme){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);
        manager.cancel(operation);
    }
}
