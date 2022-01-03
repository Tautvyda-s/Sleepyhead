package com.example.sleepyhead.games.shake;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Toast;

import com.example.sleepyhead.R;
import com.example.sleepyhead.games.touch.result;


public class ShakeActivity extends Activity implements SensorEventListener{
    private SensorManager sensorManager;
    private boolean isColor = false;
    private View view;
    private long lastUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        view = findViewById(R.id.shakeView);
        view.setBackgroundColor(Color.CYAN);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = System.currentTimeMillis();
        //Toast.makeText(getApplicationContext(),String.valueOf(accelationSquareRoot)+" "+
        //SensorManager.GRAVITY_EARTH,Toast.LENGTH_SHORT).show();

        if (accelationSquareRoot >= 2)
        {

            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            if (isColor) {
                view.setBackgroundColor(Color.MAGENTA);

            } else {
                onFinish();
                //ShakeActivity.this.finish();
            }
            isColor = !isColor;
        }

    }

    public void onFinish () {
        ShakeActivity.this.finish();
        Intent intent = new Intent (getApplicationContext(), result.class);
        int score = 30;
        intent.putExtra("SCORE", score);
        startActivity(intent);
        overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);


    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(this);
    }
}