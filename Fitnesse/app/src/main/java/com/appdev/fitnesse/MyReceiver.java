package com.appdev.fitnesse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"");
        wl.acquire();

        Toast.makeText(context,"Time to work",Toast.LENGTH_LONG).show();
        Intent i=new Intent(context,MyNotificationService.class);
        i.setData(Uri.parse("custom://"+System.currentTimeMillis()));
        wl.release();
        context.startService(i);

    }

}
