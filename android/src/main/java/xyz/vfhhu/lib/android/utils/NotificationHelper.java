package xyz.vfhhu.lib.android.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String DEFAULT_CHANNEL = "default";


    public NotificationHelper(Context base) {
        super(base);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(DEFAULT_CHANNEL,
                    DEFAULT_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan1);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addChannel(NotificationChannel chan1){
        getManager().createNotificationChannel(chan1);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification(String channel_id){
        return new Notification.Builder(getApplicationContext(), channel_id);
//        return new Notification.Builder(getApplicationContext(), channel_id)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(getSmallIcon())
//                .setAutoCancel(true);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification(String channel_id,String title, String body){
        return new Notification.Builder(getApplicationContext(), channel_id)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notify(int notify_id, Notification.Builder notification) {
        getManager().notify(notify_id, notification.build());
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
