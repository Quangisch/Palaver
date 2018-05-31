package de.web.ngthi.palaver.network.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.view.friends.FriendsActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = "==FIREBASE_MESSAGE==";
    private final String CHANNEL_ID = "Palaver Message Channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "remoteMessage: " + remoteMessage.toString());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            setupChannels();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            sendMyNotification(remoteMessage);

        }

    }

    private void sendMyNotification(RemoteMessage remoteMessage) {
        Log.d(TAG, String.format("DATA:%s\nFROM:%s\nCOLLAPSEKEY:%s\nMESSAGEID:%s",
                remoteMessage.getData(),
                remoteMessage.getFrom(),
                remoteMessage.getCollapseKey(),
                remoteMessage.getMessageId()));


        Intent intent = new Intent(this, FriendsActivity.class);
        //TODO
        String messageFrom = "Peter32";
        intent.putExtra(getString(R.string.intent_friend_message), messageFrom);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.palaver_icon)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null)
            notificationManager.notify(0, notificationBuilder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        Log.d(TAG, "setupChannels");
        CharSequence channelName = getString(R.string.app_channel_name);
        String channelDescription = getString(R.string.app_channel_description);

        NotificationChannel messageChannel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        messageChannel.setDescription(channelDescription);
        messageChannel.enableLights(true);
        messageChannel.setLightColor(Color.WHITE);
        messageChannel.enableVibration(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if(notificationManager != null) {
            notificationManager.createNotificationChannel(messageChannel);
            Log.d(TAG, "Channel created");
        }
    }

    @Override
    public void onDeletedMessages() {

    }


}
