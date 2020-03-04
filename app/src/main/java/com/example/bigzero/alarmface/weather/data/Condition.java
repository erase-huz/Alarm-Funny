package com.example.bigzero.alarmface.weather.data;

import org.json.JSONObject;

/**
 * Created by asus on 31/03/2017.
 */

public class Condition implements JSONPopulator {
    private int code;
    private int temp;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temp = data.optInt("temp");
        description = data.optString("text");

    }
}
