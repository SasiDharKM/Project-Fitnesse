package com.appdev.fitnesse;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

public class MyNotificationService extends IntentService {
    private NotificationManager manager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID=1;
    Notification notification;

    public MyNotificationService(){
        super("name");

    }
    public MyNotificationService(String name) {
        super(name);
    }


   /** @Override
    public IBinder onBind(Intent intent) {
        return null;
    }**/

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context=this.getApplicationContext();
        manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent=new Intent(this,MAinActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("test", "test");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification= new Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.fitness)
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(soundUri)
                .setContentTitle("Fitnesse")
                .setContentText("Time to work those 'Muscles'").build();
        notification.flags|=Notification.FLAG_AUTO_CANCEL|Notification.FLAG_SHOW_LIGHTS;
        notification.defaults|=Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 900;

        manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID,notification);

    }

}
