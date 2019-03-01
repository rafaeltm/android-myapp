package com.comov.myapp;


import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecondActivity extends AppCompatActivity {

    public static String FILE_DIR_NAME = "COMOV";
    public static String FILE_NAME = "Internal.txt";
    public static String FILE_NAME_PUB = "Publico.txt";
    public static String DIR_PUBLIC = "Publico";
    public static NotificationManagerCompat notmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        notmanager =  NotificationManagerCompat.from(this);
        Bundle extras = getIntent().getExtras();
        if (!extras.isEmpty()) {
            TextView txt2Screen = findViewById(R.id.textSecondScreen);
            txt2Screen.setText(extras.get("title").toString());
        }
    }

    public void saveFile2Internal(View v) {
        // FILE PATH
        File file = new File(getFilesDir(), FILE_NAME);

        // Creating and writing file internal
        String content = "Asignatura COMOV 2019";
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();

            Log.i("Internal", "Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFile2External(View v) {
        if (isExternalStorageReadable() && isExternalStorageWritable()) {
            // PUBLIC
            // Create a public path in Downloads
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), DIR_PUBLIC);
            if (!file.mkdirs()) {
                Log.e("File error", "Directory -public- not created");
            }

            // FILE PATH
            File file2 = new File(file.getPath(), FILE_DIR_NAME);
            if(!file2.exists()){
                file2.mkdir();
            }
            // Creating and writing file internal
            String content = "EXTERNO PUB COMOV 2019";
            try {
                File gpxfile = new File(file2, FILE_NAME_PUB);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(content);
                writer.flush();
                writer.close();

                Log.i("Internal", "Success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveSharedPrefs(View v) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("COMOV_KEY", "Shared pref Value");
        editor.apply(); // Async-saving.
        // editor.commit(); // Sync-saving. DO NOT CALL FROM MAIN THREADS.
    }

    public void readShared(View v) {
        TextView text = findViewById(R.id.textSecondScreen);
        text.setText(readSharedPrefs("COMOV_KEY"));
    }

    public void readInternal(View v) {
        try {
            FileInputStream inputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            inputStreamReader.close();

            TextView text = findViewById(R.id.textSecondScreen);
            text.setText(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readSharedPrefs(String key) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "Not found");
        notmanager.notify(0, createBasicNotification("Shared Prefs", "Has pulsado shared prefs", NotificationCompat.PRIORITY_DEFAULT));
        return value;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
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
