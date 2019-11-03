package com.example.tugasbesar2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FragmentCommunication, SensorEventListener {
    protected FragmentGameplay fragmentGameplay;
    protected FragmentMainMenu fragmentMainMenu;
    protected FragmentManager fragmentManager;
    protected ArrayList<Fragment> halamanFragment;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private float VALUE_DRIFT = 0.05f;
    protected int roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //atribut initialitation
        this.halamanFragment = new ArrayList<>();
        this.fragmentGameplay = new FragmentGameplay();
        this.fragmentMainMenu = new FragmentMainMenu();
        this.fragmentManager = this.getSupportFragmentManager();

        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        this.accelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.accelerometerReading = new float[9];
        this.magnetometerReading = new float[9];

        //put fragment in array
        halamanFragment.add(fragmentGameplay);
        halamanFragment.add(fragmentMainMenu);

        //begin transaction
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container,this.fragmentMainMenu)
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.accelerometer != null) {
            this.mSensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        if (this.magnetometer != null) {
            this.mSensorManager.registerListener(this, this.magnetometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mSensorManager.unregisterListener(this);
    }

    public void changePage(String halaman){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(halaman.equalsIgnoreCase("playGame")){
            if(this.fragmentGameplay.isAdded()){
                ft.show(this.fragmentGameplay);
            }
            else {
                ft.add(R.id.fragment_container,this.fragmentGameplay)
                        .addToBackStack(null);
            }
            for(int i = 0 ; i < halamanFragment.size();i++){
                if(halamanFragment.get(i) != this.fragmentGameplay){
                    ft.hide(halamanFragment.get(i));
                }
            }
        }
        else if(halaman.equalsIgnoreCase("mainMenu")){
            if(this.fragmentMainMenu.isAdded()){
                ft.show(this.fragmentMainMenu);
            }
            else {
                ft.add(R.id.fragment_container,this.fragmentMainMenu);
            }
            for(int i = 0 ; i < halamanFragment.size();i++){
                if(halamanFragment.get(i) != this.fragmentMainMenu){
                    ft.hide(halamanFragment.get(i));
                }
            }
        }
        ft.commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                this.accelerometerReading = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.magnetometerReading = event.values.clone();
                break;
        }
        final float[] rotationMatrix = new float[9];
        mSensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        final float[] orientationAngles = new float[3];
        mSensorManager.getOrientation(rotationMatrix, orientationAngles);
        float roll = orientationAngles[2];



        if (Math.abs(roll) < VALUE_DRIFT) {
            roll = 0;
        }

        this.roll = (int)(roll*500);

    }

    public int gerakKiriKanan(){
        return roll;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
