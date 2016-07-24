package com.appdev.fitnesse;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import org.w3c.dom.Text;

public class JogService extends Service {
    private final IBinder binder = new JogBinder();
    private static double distanceInMetres;
    private static Location lastLocation=null;

    public class JogBinder extends Binder{
        JogService getJog(){
                return JogService.this;
        }
    }

    public JogService() {
    }

    @Override
    public void onCreate() {
        LocationListener listener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(lastLocation==null){
                    lastLocation=location;
                }
                distanceInMetres+=location.distanceTo(lastLocation);
                lastLocation=location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                //Toast.makeText(JogService.this,"Gps Enabled",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderDisabled(String provider) {
                //Toast.makeText(JogService.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();

            }
        };
        LocationManager locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,listener);
    }

    public double getkms(){
        return this.distanceInMetres /1000;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
