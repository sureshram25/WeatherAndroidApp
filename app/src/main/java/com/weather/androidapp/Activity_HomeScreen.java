package com.weather.androidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.weather.androidapp.adapter.WeatherAdpater;
import com.weather.androidapp.model.List;
import com.weather.androidapp.model.MainObject;

import java.util.ArrayList;

public class Activity_HomeScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    String LOADURL = "http://api.openweathermap.org/data/2.5/group?id=1264527,1275339,1277333,1261481&appid=c6066e4cc92ade6f86d0245ca5be964b";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initializeView();
        setWeatherInfo("&units=metric", "C");

    }

    private void initializeView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setWeatherInfo(final String units, final String units_of_temp) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Ion.with(Activity_HomeScreen.this).load(LOADURL + units)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {

                        if (result != null) {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            Gson gson = new Gson();
                            MainObject mainObject = gson.fromJson(result.getResult().toString(), MainObject.class);
                            ArrayList<List> lists = mainObject.getList();
                            WeatherAdpater weatherAdpater = new WeatherAdpater(getApplicationContext(), lists, units, units_of_temp);
                            recyclerView.setAdapter(weatherAdpater);

                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_default:
                setWeatherInfo("", "K");
                return true;
            case R.id.action_fahrenheit:
                setWeatherInfo("&units=imperial", "F");

                return true;
            case R.id.action_celsius:
                setWeatherInfo("&units=metric", "C");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
