package com.example.bigzero.alarmface.adapter;

/**
 * Created by asus on 28/03/2017.
 */

public class alarm {
    private int id;
    private String time, ringtone;
    private int vibration, repeat, status;

    public alarm() {

    }

    public alarm(int id, String time, String ringtone, int vibration, int repeat, int status) {
        this.id = id;
        this.time = time;
        this.ringtone = ringtone;
        this.vibration = vibration;
        this.repeat = repeat;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public int getVibration() {
        return vibration;
    }

    public void setVibration(int vibration) {
        this.vibration = vibration;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
