/*
package com.example.sitegitfirebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.JsonToken;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import ir.hajj.virtualhajj_app.Utility.Config;
import ir.hajj.virtualhajj_app.Utility.NotificationUtil;
import okhttp3.ResponseBody;


public class NotifService extends FirebaseMessagingService {

    private static final String TAG=NotifService.class.getSimpleName();
    private final static String ACT = "act" ;
    private final static String ICON = "icon" ;
    private final static String LINK = "link" ;
    NotificationManager notifManager;
    String offerChannelId = "Offers";
    PendingIntent mpIntent;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            JSONObject jsonObject=new JSONObject(remoteMessage.getData());
            try {
                if(jsonObject.getString("Type").equals("web")){
                    notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent pIntent = new Intent(Intent.ACTION_VIEW);
                    pIntent.setData(Uri.parse(jsonObject.getString("url")));
                    mpIntent = PendingIntent.getActivity(this, 0, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    createNotifChannel();
                    pendingNotification(jsonObject.getString("title"),jsonObject.getString("detail"),jsonObject.getString("pic"));
                }
                else if(jsonObject.getString("Type").equals("live")) {
                    notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent pIntent = new Intent(getApplicationContext(),LiveActivity.class);
                    mpIntent = PendingIntent.getActivity(this, 0, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    createNotifChannel();
                    pendingNotification(jsonObject.getString("title"),jsonObject.getString("detail"),jsonObject.getString("pic"));
                }
                else if(jsonObject.getString("Type").equals("popup")){
                    notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent pIntent = new Intent(getApplicationContext(),MainActivity.class);
                    pIntent.putExtra("title",jsonObject.getString("title"));
                    pIntent.putExtra("message",jsonObject.getString("message"));
                    pIntent.putExtra("pic",jsonObject.getString("pic"));
                    mpIntent = PendingIntent.getActivity(this, 0, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    createNotifChannel();
                    pendingNotification(jsonObject.getString("title"),jsonObject.getString("detail"),jsonObject.getString("pic"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void createNotifChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String offerChannelName = "کاروان مجازی حج";
            String offerChannelDescription= "";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifChannel = new NotificationChannel(offerChannelId, offerChannelName, offerChannelImportance);
            notifChannel.setDescription(offerChannelDescription);
            //notifChannel.enableVibration(true);
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.GREEN);

            notifManager.createNotificationChannel(notifChannel);

        }

    }
    public void simpleNotification(String title,String message) {

        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this,offerChannelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{100,500,500,500,500,500})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        sNotifBuilder.setAutoCancel(true);
        notifManager.notify(1, sNotifBuilder.build());

    }

    public void pendingNotification(String title,String message,String picurl) {

        try {
            URL url1 = new URL(picurl);
            Bitmap image = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

            NotificationCompat.Builder pNotifBuilder = new NotificationCompat.Builder(this,offerChannelId)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setVibrate(new long[]{100,500,500,500,500,500})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(image)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(mpIntent);
            pNotifBuilder.setAutoCancel(true);
            notifManager.notify(2, pNotifBuilder.build());

        } catch(IOException e) {
            System.out.println(e);
        }



    }
    */
/**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     *//*

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        ApiProvider apiProvider=new ApiProvider();
        ApiServices apiServices=apiProvider.GetApiServices();

        Call<ResponseBody> call=apiServices.SetToken(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
*/
