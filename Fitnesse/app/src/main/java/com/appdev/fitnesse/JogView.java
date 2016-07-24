package com.appdev.fitnesse;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class JogView extends Activity {

    ListView cList;

    private RoutineDbHelper cdbh;
    String[] time,dist,spd;

    List<String> timeList=new ArrayList<>();
    List<String> distList=new ArrayList<>();
    List<String> spdList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jog_view);

        cList=(ListView)findViewById(R.id.jog_list);
        cdbh= new RoutineDbHelper(this,null,null,1);
        fillData();
        time= new String[timeList.size()];
        dist= new String[distList.size()];
        spd= new String[spdList.size()];
        int temp =0;
        while(temp<timeList.size()){
            spd[temp]=spdList.get(temp);
            time[temp]=timeList.get(temp);
            dist[temp]=distList.get(temp);
            temp++;
        }
        ListAdapter myAdapter =new CustomAdapter(JogView.this,time,dist,spd);
        cList.setAdapter(myAdapter);
    }

    private void fillData() {

        Cursor result=cdbh.fetchAllRows();

        if(result.getCount()==0){
            return;
        }
        while(result.moveToNext()){
            spdList.add(result.getString(2));
            timeList.add(result.getString(0));
            distList.add(result.getString(1));

        }
    }
}
