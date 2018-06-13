package com.weather.androidapp.model;

/**
 * Created by Venkat on 22-02-2018.
 */

public class WModel {
    float temperature;
    double rain=0;
    String time="";
    String icon;
    int maxrain=0;

    public int getMaxrain() {
        return maxrain;
    }

    public void setMaxrain(int maxrain) {
        this.maxrain = maxrain;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
