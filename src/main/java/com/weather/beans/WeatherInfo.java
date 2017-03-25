package com.weather.beans;

import java.io.Serializable;

public class WeatherInfo implements Serializable {
    private static final long serialVersionUID = -2749977649306134186L;

    private String sensorName;
    private Float temperature;
    private Float humidity;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "sensorName='" + sensorName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
