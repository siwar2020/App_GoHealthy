package com.example.gohealthlyapplication.alarmreminder.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.gohealthlyapplication.R;
import com.example.gohealthlyapplication.alarmreminder.AddReminderActivity;

import com.example.gohealthlyapplication.alarmreminder.data.AlarmReminderContract;



public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;
    String description;

    Cursor cursor;
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();
        Intent action = new Intent(this, AddReminderActivity.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        if(uri != null){
            cursor = getContentResolver().query(uri, null, null, null, null);
        }
        try {
            if (cursor != null && cursor.moveToFirst()) {
                description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle("It's Medicine Time ")
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_access_time_grey600_24dp)
                .setContentIntent(operation)
                .setVibrate(new long[] {1000,1000,1000,1000,1000})
                .setSound(Settings.System.DEFAULT_RINGTONE_URI)
                .setAutoCancel(true)
                .build();
        manager.notify(NOTIFICATION_ID, note);
    }
}