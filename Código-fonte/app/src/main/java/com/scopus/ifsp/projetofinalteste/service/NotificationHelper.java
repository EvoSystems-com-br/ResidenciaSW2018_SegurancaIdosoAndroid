/*
* Copyright 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.scopus.ifsp.projetofinalteste.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.activity.AddReminderActivity;
import com.scopus.ifsp.projetofinalteste.model.Alarme;
import com.scopus.ifsp.projetofinalteste.model.AlarmeDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;

/**
 * Helper class to manage notification channels, and create notifications.
 */
class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String PRIMARY_CHANNEL = "Alarme";
    private long[] mVibrattePattern = {0,200,200,300};
    //public static final String SECONDARY_CHANNEL = "second";

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param ctx The application context0
     */
    public NotificationHelper(Context ctx) {
        super(ctx);

        NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                getString(R.string.noti_channel_default), NotificationManager.IMPORTANCE_DEFAULT);
        chan1.setLightColor(Color.GREEN);
        chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        chan1.enableVibration(true);
        chan1.setVibrationPattern(mVibrattePattern);
        getManager().createNotificationChannel(chan1);

      /*  NotificationChannel chan2 = new NotificationChannel(SECONDARY_CHANNEL,
                getString(R.string.noti_channel_second), NotificationManager.IMPORTANCE_HIGH);
        chan2.setLightColor(Color.BLUE);
        chan2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(chan2);*/
    }


    /**
     * Get a notification of type 1
     *
     * Provide the builder rather than the notification it's self as useful for making notification
     * changes.
     *
     * @param title the title of the notification
     * @param body the body text for the notification
     * @return the builder as it keeps a reference to the notification (since API 24)
     */

    public Notification.Builder getNotification1(String title, String body, int idAlarme) {

        //Componentes BD
        DaoSession daoSession = ((App) this.getApplicationContext()).getDaoSession();
        AlarmeDao alarmeDao = daoSession.getAlarmeDao();
        Alarme alarme = alarmeDao.load((long)idAlarme);

        //Exibe notificação para a view em detalhes
        Intent action = new Intent(this, AddReminderActivity.class);
        action.putExtra("alarme",alarme);

        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Builder(getApplicationContext(), PRIMARY_CHANNEL)
                 .setContentTitle(title) // titulo da notificação
                 .setContentText(body) // descrição
                 .setSmallIcon(getSmallIcon()) // ícone
                 .setContentIntent(operation) // Intent ao clicar na notificação
 //                .addAction(builderAction()) // cria botão na notificação
                 .setAutoCancel(false);
    }

    /**
     * Build notification for secondary channel.
     *
     * @param title Title for notification.
     * @param body Message for notification.
     * @return A Notification.Builder configured with the selected channel and details
     */
    /*
    public Notification.Builder getNotification2(String title, String body) {
        return new Notification.Builder(getApplicationContext(), SECONDARY_CHANNEL)
                 .setContentTitle(title)
                 .setContentText(body)
                 .setSmallIcon(getSmallIcon())
                 .setAutoCancel(true);
    }*/

    /**
     * Send a notification.
     *
     * @param id The ID of the notification
     * @param notification The notification object
     */
    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

    /**
     * Get the small icon for this app
     *
     * @return The small icon resource id
     */
    private int getSmallIcon() {
        return R.mipmap.ic_launcher_foreground;
    }

    /**
     * Get the notification manager.
     *
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
