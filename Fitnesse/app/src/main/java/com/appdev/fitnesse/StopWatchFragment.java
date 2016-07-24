package com.appdev.fitnesse;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopWatchFragment extends Fragment implements View.OnClickListener {
    private int seconds=0;
    private boolean running;
    private boolean wasRunning;

    public StopWatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("seconds");
            wasRunning=savedInstanceState.getBoolean("seconds");
            if(wasRunning){
                running = true;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_stop_watch,container,false);
        runTimer(layout);
        Button startB=(Button)layout.findViewById(R.id.start_button);
        Button stopB=(Button)layout.findViewById(R.id.stop_button);
        Button resetB=(Button)layout.findViewById(R.id.reset_button);
        startB.setOnClickListener(this);
        resetB.setOnClickListener(this);
        stopB.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning=running;
        running=false;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    public void onClickStart(View view){
        running =true;

    }
    public void onClickStop(View view){
        running=false;
    }
    public void onClickReset(View view){
        running=false;

        seconds=0;
    }

    private void runTimer(View view){
        final TextView timeView=(TextView)view.findViewById(R.id.time_view);
        final Handler handler=new Handler();
        handler.post(new Runnable(){

            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time= String.format("%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_button:
                onClickStart(v);
                break;
            case R.id.stop_button:
                onClickStop(v);
                break;
            case R.id.reset_button:
                onClickReset(v);
                break;
        }
    }
}
