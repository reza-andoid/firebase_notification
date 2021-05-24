package com.example.sitegitfirebase;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

public class FCMExampleMsgService extends FirebaseMessagingService {
    private static final String TAG = "FCMExampleMsgService";
    NotificationManager notificationManager;
    String offerChannelId = "offers";

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG, "Message Received from : " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message size : " + remoteMessage.getData().size());

            for (String key : remoteMessage.getData().keySet()) {
                Log.i(TAG, "key : " + key + " data : " +
                        remoteMessage.getData().get(key));

                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                try {
                    if (jsonObject.getString("message").equals("buy")) {
                        sendNotification("خرید موفقیت آمیز", "از خرید شما سپاس گذاریم", R.drawable.icon, 0);
                    }else {                                                 //ورودی 0 عکس مورد نظر رو نمیاره
                        sendNotification("data notification", "notification is not found data", 0, R.drawable.icon8);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void sendNotification(String messageTitle, String messageBody, int idIconSmail, int idIconlarge) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel("my_notification", "n_channel", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // big text notification
        NotificationCompat.BigTextStyle bStyle = new NotificationCompat.BigTextStyle()
                .bigText("لورم ایپسوم متنی است که ساختگی برای طراحی و چاپ آن مورد است. صنعت چاپ زمانی لازم بود شرایطی شما باید فکر ثبت نام و طراحی، لازمه خروج می باشد ")
                .setBigContentTitle("نوتیفیکیشن طولانی")
                .setSummaryText("خلاصه متن طولانی");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
//                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .addAction(R.drawable.ic_arow, "Buy", pendingIntent)
                .addAction(R.drawable.comment, "Product details", pendingIntent)        // set btn notification
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.parseColor("#3F5996"));
                if (idIconSmail != 0)
                    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), idIconSmail));
                if (idIconlarge != 0) {
                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(getResources(), idIconlarge))); // change big image
                }
        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }

}
