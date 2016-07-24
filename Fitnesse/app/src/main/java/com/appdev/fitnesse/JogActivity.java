package com.appdev.fitnesse;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JogActivity extends Activity {

    private JogService jog;
    private boolean bound=false;
    private int seconds=0, i=0;
    private boolean running;

    Button play,stop;
    MediaPlayer songPlay;

    private ServiceConnection serviceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            JogService.JogBinder jogBinder=(JogService.JogBinder)service;
            jog=jogBinder.getJog();
            bound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound= false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_jog);
        play=(Button)findViewById(R.id.play_button);
        stop=(Button)findViewById(R.id.stop_button);
        songPlay=MediaPlayer.create(this,R.raw.engo);
        stop.setEnabled(false);

        watchDistance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,JogService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        running= true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        songPlay.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //songPlay.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bound){
            unbindService(serviceConnection);
            bound= false;
        }
        running =false;
        i=0;

    }
    public void onClickPlay(View v){
        songPlay.start();
        play.setEnabled(false);
        stop.setEnabled(true);

    }

    public void onClickStop(View v){
        if(songPlay.isPlaying()){
            songPlay.pause();
            play.setEnabled(true);
            stop.setEnabled(false);
        }



    }

    public void stopJog(View v){
        RoutineDbHelper rdb= new RoutineDbHelper(this,null,null,1);
        final TextView disView = (TextView)findViewById(R.id.distance);
        final TextView spdView=(TextView)findViewById(R.id.jog_spd);
        String dis=disView.getText().toString();
        String spd=spdView.getText().toString();
        String timeStamp = new SimpleDateFormat("dd/MM/yy(HH:mm)", Locale.getDefault()).format(new Date());
        rdb.createContact(timeStamp,dis,spd);
        Toast.makeText(this,"Your Jog has been Saved",Toast.LENGTH_SHORT).show();
        seconds=0;
        running= false;
        if(bound){
            unbindService(serviceConnection);
            bound= false;
        }
        if(songPlay.isPlaying()){
            songPlay.pause();
            play.setEnabled(true);
            stop.setEnabled(false);
        }
        Intent intent=new Intent(this,MAinActivity.class);
        startActivity(intent);

    }


    private void watchDistance(){
        final double spdDistance;
        final TextView distanceView = (TextView)findViewById(R.id.distance);
        final TextView speedView=(TextView)findViewById(R.id.jog_spd);
        final TextView timeView=(TextView)findViewById(R.id.jog_time);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance =0.0;
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;

                if(jog!=null){
                    distance = jog.getkms();
                }
                if(running){
                    seconds++;
                }
                String distanceStr=String.format("%1$,.2f Kms",distance);
                String time= String.format("%d:%02d:%02d",hours,minutes,secs);
                distanceView.setText(distanceStr);
                timeView.setText(time);
                i++;

                if(i>4)
                {
                   String speed= String.format("%1$,.2f m/s",(distance*1000/seconds));
                    speedView.setText(speed);
                }
                else if(i<=4){
                    speedView.setText("waiting..");
                }
                handler.postDelayed(this,1000);
            }
        });
    }
}
