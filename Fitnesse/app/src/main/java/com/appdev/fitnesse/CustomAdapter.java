package com.appdev.fitnesse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SasiDKM on 19-07-2016.
 */
public class CustomAdapter extends ArrayAdapter {
    Context context;
    String [] dist,spd;

    TextView jspd,jdist,jtime;

    public CustomAdapter(Context context,String[] ti,String[] di, String[]sp) {
        super(context, R.layout.jog_row,ti);
        this.dist=di;
        this.spd=sp;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater=LayoutInflater.from(getContext());
        View myView= myInflater.inflate(R.layout.jog_row,parent,false);
        final String Time=getItem(position).toString();

        jtime=(TextView)myView.findViewById(R.id.timView);
        jdist=(TextView)myView.findViewById(R.id.disView);
        jspd=(TextView)myView.findViewById(R.id.spdView);
        jtime.setText(Time);
        jdist.setText(dist[position]);
        jspd.setText(spd[position]);

        return myView;
    }
}
