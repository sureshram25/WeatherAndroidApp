package com.weather.androidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Activity_WeatherDetails extends AppCompatActivity {

    TextView txt_humidity, txt_wind, txt_pressure, txt_temp_details, txt_placename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__weather_details);
        init();
        loadData();
    }

    private void loadData() {
        if (getIntent() != null) {
            String humidity = getIntent().getStringExtra("humidity");
            String wind = getIntent().getStringExtra("wind");
            String pressure = getIntent().getStringExtra("pressure");
            String temp = getIntent().getStringExtra("temp");
            String name = getIntent().getStringExtra("name");

            txt_humidity.setText("Humidity : " + humidity);
            txt_wind.setText("Wind : " + wind);
            txt_pressure.setText("Pressure : " + pressure);
            txt_temp_details.setText(temp);
            txt_placename.setText(name);
        }
    }

    private void init() {
        txt_humidity = findViewById(R.id.txt_humidity);
        txt_wind = findViewById(R.id.txt_wind);
        txt_pressure = findViewById(R.id.txt_pressure);
        txt_temp_details = findViewById(R.id.txt_temp_details);
        txt_placename = findViewById(R.id.txt_placename);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //listener for home
        if (id == android.R.id.home) {
            this.finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
