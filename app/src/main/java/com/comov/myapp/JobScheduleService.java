package com.comov.myapp;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class JobScheduleService extends JobService {
    public static NotificationManagerCompat notmanager;

    public JobScheduleService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        downloadSomething(params);
        Log.i("JOB", "Iniciada");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("JOB", "Terminada");
        return true;
    }

    private void downloadSomething(JobParameters params) {
        notmanager =  NotificationManagerCompat.from(this);
        notmanager.notify(1, createBasicNotification("JobSchedule", "Descargando un archivo...", NotificationCompat.PRIORITY_DEFAULT));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                notmanager.notify(2, createBasicNotification("JobSchedule", "Finalizado...", NotificationCompat.PRIORITY_DEFAULT));
            }
        }, 5000);   //5 segundos
        jobFinished(params, false);
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
