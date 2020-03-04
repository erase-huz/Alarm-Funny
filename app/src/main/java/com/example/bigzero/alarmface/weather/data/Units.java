package com.example.bigzero.alarmface.weather.data;

import org.json.JSONObject;

/**
 * Created by asus on 31/03/2017.
 */

public class Units implements JSONPopulator {
    //đơn vị của nhiệt độ C/F
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
