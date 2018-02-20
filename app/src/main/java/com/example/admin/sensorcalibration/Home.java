package com.example.admin.sensorcalibration;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors_sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d("SIZE", sensors_sensors.size()+"");
    }

    @OnClick(R.id.prox) void prox(){

        Intent intent = new Intent(Home.this, SensorWorks.class);
        intent.putExtra("tag", 1);
        startActivity(intent);
    }

    @OnClick(R.id.accelero) void accelero(){

        Intent intent = new Intent(Home.this, SensorWorks.class);
        intent.putExtra("tag", 2);
        startActivity(intent);
    }

    @OnClick(R.id.gyro) void gyro(){

        Intent intent = new Intent(Home.this, SensorWorks.class);
        intent.putExtra("tag", 3);
        startActivity(intent);
    }

    @OnClick(R.id.light) void light(){

        Intent intent = new Intent(Home.this, SensorWorks.class);
        intent.putExtra("tag", 4);
        startActivity(intent);
    }
}
