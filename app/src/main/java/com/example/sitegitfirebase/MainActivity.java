package com.example.sitegitfirebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "FCMExample: ";

    private String m_FCMtoken;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sendNotification("خرید موفقیت آمیز", "از خرید شما سپاس گذاریم");

        // TODO: get the FCM instance default token
        m_FCMtoken = FirebaseInstanceId.getInstance().getToken();
        tvMsg = (TextView) findViewById(R.id.textView2);

        // TODO: Log the token to debug output so we can copy it
        ((Button) findViewById(R.id.btnLogToken)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "FCm example token : " + m_FCMtoken);
            }
        });

        // TODO: subscribe to a topic
        ((Button) findViewById(R.id.btnSubscribe)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseMessaging.getInstance().subscribeToTopic("test-topic");
                sendNotification("title", "message");
            }
        });

        // TODO: unsubscribe from a topic
        ((Button) findViewById(R.id.btnUnsubscribe)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("test-topic");
            }
        });

        // TODO: When the activity starts up, look for intent information
        // that may have been passed in from the Notification tap
        if (getIntent().getExtras() != null) {
            String info = "";
            for (String key : getIntent().getExtras().keySet()) {
                Object val = getIntent().getExtras().get(key);
                info += key + " : " + val + "\n";
            }
            tvMsg.setText(info);
        } else {
            tvMsg.setText("No launch information");
        }
    }


    private void sendNotification(String messageTitle, String messageBody) {
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
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.icon8))) // change big image
                .setColor(Color.parseColor("#3F5996"));

        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }
}
