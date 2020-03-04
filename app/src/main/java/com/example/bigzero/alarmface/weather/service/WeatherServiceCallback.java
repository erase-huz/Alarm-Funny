package com.example.bigzero.alarmface.weather.service;


import com.example.bigzero.alarmface.weather.data.Channel;

/**
 * Created by asus on 31/03/2017.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);


    void serviceFailure(Exception exception);
}
