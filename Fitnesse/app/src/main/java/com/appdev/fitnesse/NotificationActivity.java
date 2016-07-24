package com.appdev.fitnesse;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationActivity extends Activity {
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_notification);
    }

    public void onClickSet(View v){
        EditText hour=(EditText)findViewById(R.id.editHour);
        EditText min=(EditText)findViewById(R.id.editMin);
        String Min=min.getText().toString();
        String Hour =hour.getText().toString();
        if(Min.equals("")){
            Min="0";
        }
        if(Hour.equals("")){
            Hour="6";
        }
        int MIN =Integer.valueOf(Min);
        int HOUR =Integer.valueOf(Hour);
        if(MIN<0 || MIN>=60 ||HOUR<0 || HOUR>=24){
            Toast.makeText(this,"Enter a valid Time",Toast.LENGTH_SHORT).show();
        }
        else{
            callNoti(HOUR,MIN);
        }
        Intent intent=new Intent(this,MAinActivity.class);
        startActivity(intent);


    }

    public void callNoti(int h,int m){
        Intent myIntent = new Intent(NotificationActivity.this, MyReceiver.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        myIntent.setData(Uri.parse("custom://"+System.currentTimeMillis()));

        Calendar now =Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, 0);
        if(now.after(calendar)){
            calendar.add(Calendar.DATE,1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}
