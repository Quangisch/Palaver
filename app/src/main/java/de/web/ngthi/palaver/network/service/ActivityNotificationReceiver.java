package de.web.ngthi.palaver.network.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import de.web.ngthi.palaver.mvp.view.BaseActivity;

public class ActivityNotificationReceiver extends BroadcastReceiver {

    private static final String TAG = ActivityNotificationReceiver.class.getSimpleName();
    public static final String CURRENT_ACTIVITY_ACTION = "current.activity.action";
    public static final IntentFilter CURRENT_ACTIVITY_RECEIVER_FILTER = new IntentFilter(CURRENT_ACTIVITY_ACTION);

    private BaseActivity receivingActivity;

    public ActivityNotificationReceiver(BaseActivity activity) {
        this.receivingActivity = activity;
    }

    @Override
    public void onReceive(Context sender, Intent intent) {
        Log.v(TAG, "onReceive: finishing:" + receivingActivity.getClass().getSimpleName());
        receivingActivity.getPresenter().onReceiveBroadcastNotification();
    }
}
