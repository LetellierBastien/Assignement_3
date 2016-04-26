package com.example.english.test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    Button buttonStart;
    TextView txt1,txt2, txt3,txt4;
    private boolean started=false;
    LocationManager locationManager;
    LocationListener locationListener;
    ArrayList<Location> loca = new ArrayList<Location>();
    CustomView cstVw;
    private Timer timer;
    private int call=0;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = (TextView) findViewById(R.id.txt1);

        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);

        txt2.setText(txt2.getText() + "N/A");
        txt3.setText(txt3.getText() + "N/A");
        txt4.setText(txt4.getText() + "N/A");

        cstVw = (CustomView) findViewById(R.id.custView);

        buttonStart = (Button) findViewById(R.id.button1);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setText(started? "Start tracking":"Stop tracking");
                started = !started;
                if (started)
                    gpsStart();
                else
                    gpsStop();


            }
            });

         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                loca.add(location);
                if (loca.size()>100)
                {
                    loca.remove(0);
                }
                cstVw.initLocaTab(loca);

                float x = 0;
                for(int i=0;i<loca.size();i++)
                    x = x+loca.get(i).getSpeed();

                x=x/loca.size();
                txt2.setText("Current Speed: " + location.getSpeed()*3.6 + " Km/h");
                txt3.setText("Average Speed: " + x*3.6 + " Km/h");

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
    }

    public void gpsStart() {
        try
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            txt1.setText("GPS Actived");
            timerOn();

        } catch (SecurityException e) {

        }

    }

    public void gpsStop() {
           try {
               locationManager.removeUpdates(locationListener);
               txt1.setText("GPS inactive");
               timer.cancel();
           }
           catch (SecurityException e) {

           }
    }


    private void timerOn()
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt4.setText("Overall Time " + call + "s");
                        call++;
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
