package com.example.bigzero.alarmface.weather.data;

import org.json.JSONObject;

/**
 * Created by asus on 31/03/2017.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
