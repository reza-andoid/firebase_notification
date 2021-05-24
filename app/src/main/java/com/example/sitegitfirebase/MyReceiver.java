package com.example.sitegitfirebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class MyReceiver extends BroadcastReceiver {

    String CHANNEL_ID = "test";
    NotificationManager notifManager;
    String offerChannelId = "Offers";
    PendingIntent mpIntent;
    RemoteMessage remoteMessage;
    String title, message;

    public MyReceiver( String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        createNotificationChannel(context);
//        Toast.makeText(context, "Notification", Toast.LENGTH_SHORT).show();
//        JSONObject jsonObject = new JSONObject(remoteMessage.getData());
//        notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent pIntent = new Intent(context, MainActivity.class);
//        mpIntent = PendingIntent.getActivity(context, 0, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        createNotifChannel();
//        pendingNotification("title", "message", "https://freeiconshop.com/wp-content/uploads/edd/notification-flat.png", context);
        sendNotification(title, message, context);
    }

    private void createNotifChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String offerChannelName = "کاروان مجازی حج";
            String offerChannelDescription = "";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifChannel = new NotificationChannel(offerChannelId, offerChannelName, offerChannelImportance);
            notifChannel.setDescription(offerChannelDescription);
            //notifChannel.enableVibration(true);
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.GREEN);

            notifManager.createNotificationChannel(notifChannel);

        }
    }

    public void pendingNotification(String title, String message, String picurl, Context context) {

        try {
            URL url1 = new URL(picurl);
            Bitmap image = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

            NotificationCompat.Builder pNotifBuilder = new NotificationCompat.Builder(context, offerChannelId)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setVibrate(new long[]{100, 500, 500, 500, 500, 500})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(image)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(mpIntent);
            pNotifBuilder.setAutoCancel(true);
            notifManager.notify(2, pNotifBuilder.build());

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void simpleNotification(String title, String message, Context context) {

        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(context, offerChannelId)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{100, 500, 500, 500, 500, 500})
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        sNotifBuilder.setAutoCancel(true);
        notifManager.notify(1, sNotifBuilder.build());

    }

    private void sendNotification(String messageTitle,String messageBody, Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel=new NotificationChannel("my_notification","n_channel",NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background))
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.RED);

        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }

//    private void createNotificationChannel(Context context) {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Name";
//            String description = "Description";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            ShowNotification("test title", "test desctiption", context);
//        }
//    }
//
//    public void ShowNotification(String textTitle, String textContent, Context mContext){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//    }

}
