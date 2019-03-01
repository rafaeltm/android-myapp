package com.comov.myapp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class Background extends Service {
    public static NotificationManagerCompat notmanager;
    private MediaPlayer mPlayer;

    public Background() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Servicio", "Servicio -Background- creado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notmanager =  NotificationManagerCompat.from(this);
        Log.i("Servicio", "Servicio -Background- inciado");
        notmanager.notify(0, createBasicNotification("Service", "Reproduciendo SONIC Final zone", NotificationCompat.PRIORITY_DEFAULT));
        this.mPlayer = MediaPlayer.create(this, R.raw.finalzone);
        this.mPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mPlayer.stop();
        Log.i("Servicio", "Servicio -Background- terminado");
        notmanager.notify(0, createBasicNotification("Service", "Servicio terminado", NotificationCompat.PRIORITY_DEFAULT));
    }

    private Notification createBasicNotification(String title, String content, int priority) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(priority)
                .setAutoCancel(true);
        return builder.build();
    }
}
