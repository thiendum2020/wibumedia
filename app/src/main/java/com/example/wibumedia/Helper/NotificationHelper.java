package com.example.wibumedia.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.wibumedia.R;

public class NotificationHelper extends ContextWrapper {
    private static final String EDMT_CHANEL_ID = "com.example.wibumedia.EDMTDev";
    private static final String EDMT_CHANEL_NAME = "WibuMedia";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChanel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChanel() {
        NotificationChannel edmtChanel = new NotificationChannel(EDMT_CHANEL_ID, EDMT_CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        edmtChanel.enableLights(false);
        edmtChanel.enableVibration(true);
        edmtChanel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManerger().createNotificationChannel(edmtChanel);
    }

    private NotificationManager getManerger() {
        if (manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification(String title, String body, PendingIntent contentIntent, Uri soundUri){
        return new Notification.Builder(getApplicationContext(),EDMT_CHANEL_ID)
                .setContentIntent(contentIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(false);
    }
}
