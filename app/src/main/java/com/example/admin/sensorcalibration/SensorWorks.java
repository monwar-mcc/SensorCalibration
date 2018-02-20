package com.example.admin.sensorcalibration;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class SensorWorks extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor proximitySensor,gyroscopeSensor, acceleromotorSensor, lightSensor;

    SensorEventListener proximitySensorListener, gyroscopeSensorListener, acceleromotorSensorListener, lightSensorListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int checker=getIntent().getIntExtra("tag",0);
        initVariables();
        setView(checker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void initVariables(){
        sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        gyroscopeSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        acceleromotorSensor=
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }
    public void setView(int checker){
        switch (checker){
            case 1:
                setContentView(R.layout.proximity);
                proximitySensorListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                            // Detected something nearby
                            getWindow().getDecorView().setBackgroundColor(Color.RED);
                            Log.d("value", sensorEvent.values[0]+"");
                        } else {
                            // Nothing is nearby
                            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {
                        Log.d("value", i+"");
                    }
                };

                sensorManager.registerListener(proximitySensorListener,
                        proximitySensor, 2 * 1000 * 1000);

                break;
            case 2:
                setContentView(R.layout.accelero);
                final LineChart chart = (LineChart) findViewById(R.id.chart);
                acceleromotorSensorListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                            float x = sensorEvent.values[0];
                            float y = sensorEvent.values[1];
                            float z = sensorEvent.values[2];
                            Log.d("X",x+"");
                            Log.d("Y", y+"");
                            List<Entry> entries = new ArrayList<Entry>();
                            entries.add(new Entry(x,y));
                            LineDataSet dataSet = new LineDataSet(entries, "Acceleromotor");
                            dataSet.setColor(R.color.colorAccent);
                            LineData lineData = new LineData(dataSet);
                            chart.setData(lineData);
                            chart.notifyDataSetChanged();
                            chart.animateX(5000);
                            chart.invalidate();
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {

                    }
                };
                sensorManager.registerListener(acceleromotorSensorListener,
                        acceleromotorSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 3:
                setContentView(R.layout.gyro);
                gyroscopeSensorListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if(sensorEvent.values[2] > 0.5f) { // anticlockwise
                            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                        } else if(sensorEvent.values[2] < -0.5f) { // clockwise
                            getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {
                    }
                };

                sensorManager.registerListener(gyroscopeSensorListener,
                        gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 4:
                setContentView(R.layout.light);

                lightSensorListener=new SensorEventListener() {

                  final Chart chart = (LineChart) findViewById(R.id.chart);
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        Log.d("light", sensorEvent.values[0]+"");

                        List<Entry> entries = new ArrayList<Entry>();
                        entries.add(new Entry(sensorEvent.values[0], 0));
                        LineDataSet dataSet = new LineDataSet(entries, "LightSensor");
                        dataSet.setColor(R.color.colorAccent);
                        LineData lineData = new LineData(dataSet);
                        chart.setData(lineData);
                        chart.notifyDataSetChanged();
                        chart.animateX(5000);
                        chart.invalidate();


                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {

                    }
                };
                sensorManager.registerListener(lightSensorListener,
                        lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
        sensorManager.unregisterListener(gyroscopeSensorListener);
        sensorManager.unregisterListener(acceleromotorSensorListener);
        sensorManager.unregisterListener(lightSensorListener);
    }
}
