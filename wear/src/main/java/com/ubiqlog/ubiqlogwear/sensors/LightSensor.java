package com.ubiqlog.ubiqlogwear.sensors;

import android.hardware.SensorEvent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import android.content.Intent;
import android.app.Service;
import android.util.Log;
import android.widget.Toast;

import com.ubiqlog.ubiqlogwear.core.DataAcquisitor;
import com.ubiqlog.ubiqlogwear.utils.JsonEncodeDecode;

import java.util.Date;

/**
 * Created by prajnashetty on 10/30/14.
 */

public class LightSensor extends Service {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private long lastUpdate;
    SensorEventListener listen;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sensorManager.registerListener(listen, lightSensor, 2);
        lastUpdate = System.currentTimeMillis();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "Started Light Logging", Toast.LENGTH_SHORT).show();
        super.onCreate();

        listen = new SensorListen();
        sensorManager = (SensorManager) getApplicationContext()
                .getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE );
    }



    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(listen);
        Toast.makeText(this, "Destroy Light Logging", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public class SensorListen implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            Date _currentDate = new Date();
            if (event.accuracy == 3) {
                String jsonString = JsonEncodeDecode.EncodeLight(event.values[0], _currentDate);
                DataAcquisitor.dataBuff.add(jsonString);
                Log.i("Light-Logging", jsonString);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    }
}