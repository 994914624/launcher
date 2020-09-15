package com.yzk.launcher.entity;

/**
 * Created by yzk on 2020/8/26
 */

public class WeatherEntity {

    /**
     * day_weather : 晴
     * day_weather_code : 00
     * day_weather_short : 晴
     * day_wind_direction : 北风
     * day_wind_direction_code : 8
     * day_wind_power : 3
     * day_wind_power_code : 0
     * max_degree : 32
     * min_degree : 21
     * night_weather : 晴
     * night_weather_code : 00
     * night_weather_short : 晴
     * night_wind_direction : 西北风
     * night_wind_direction_code : 7
     * night_wind_power : 3
     * night_wind_power_code : 0
     * time : 2020-08-26
     */

    private String day_weather;
    private String day_weather_code;
    private String day_weather_short;
    private String day_wind_direction;
    private String day_wind_direction_code;
    private String day_wind_power;
    private String day_wind_power_code;
    private String max_degree;
    private String min_degree;
    private String night_weather;
    private String night_weather_code;
    private String night_weather_short;
    private String night_wind_direction;
    private String night_wind_direction_code;
    private String night_wind_power;
    private String night_wind_power_code;
    private String time;

    public String getDay_weather() {
        return day_weather;
    }

    public void setDay_weather(String day_weather) {
        this.day_weather = day_weather;
    }

    public String getDay_weather_code() {
        return day_weather_code;
    }

    public void setDay_weather_code(String day_weather_code) {
        this.day_weather_code = day_weather_code;
    }

    public String getDay_weather_short() {
        return day_weather_short;
    }

    public void setDay_weather_short(String day_weather_short) {
        this.day_weather_short = day_weather_short;
    }

    public String getDay_wind_direction() {
        return day_wind_direction;
    }

    public void setDay_wind_direction(String day_wind_direction) {
        this.day_wind_direction = day_wind_direction;
    }

    public String getDay_wind_direction_code() {
        return day_wind_direction_code;
    }

    public void setDay_wind_direction_code(String day_wind_direction_code) {
        this.day_wind_direction_code = day_wind_direction_code;
    }

    public String getDay_wind_power() {
        return day_wind_power;
    }

    public void setDay_wind_power(String day_wind_power) {
        this.day_wind_power = day_wind_power;
    }

    public String getDay_wind_power_code() {
        return day_wind_power_code;
    }

    public void setDay_wind_power_code(String day_wind_power_code) {
        this.day_wind_power_code = day_wind_power_code;
    }

    public String getMax_degree() {
        return max_degree;
    }

    public void setMax_degree(String max_degree) {
        this.max_degree = max_degree;
    }

    public String getMin_degree() {
        return min_degree;
    }

    public void setMin_degree(String min_degree) {
        this.min_degree = min_degree;
    }

    public String getNight_weather() {
        return night_weather;
    }

    public void setNight_weather(String night_weather) {
        this.night_weather = night_weather;
    }

    public String getNight_weather_code() {
        return night_weather_code;
    }

    public void setNight_weather_code(String night_weather_code) {
        this.night_weather_code = night_weather_code;
    }

    public String getNight_weather_short() {
        return night_weather_short;
    }

    public void setNight_weather_short(String night_weather_short) {
        this.night_weather_short = night_weather_short;
    }

    public String getNight_wind_direction() {
        return night_wind_direction;
    }

    public void setNight_wind_direction(String night_wind_direction) {
        this.night_wind_direction = night_wind_direction;
    }

    public String getNight_wind_direction_code() {
        return night_wind_direction_code;
    }

    public void setNight_wind_direction_code(String night_wind_direction_code) {
        this.night_wind_direction_code = night_wind_direction_code;
    }

    public String getNight_wind_power() {
        return night_wind_power;
    }

    public void setNight_wind_power(String night_wind_power) {
        this.night_wind_power = night_wind_power;
    }

    public String getNight_wind_power_code() {
        return night_wind_power_code;
    }

    public void setNight_wind_power_code(String night_wind_power_code) {
        this.night_wind_power_code = night_wind_power_code;
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "day_weather='" + day_weather + '\'' +
                ", day_wind_direction='" + day_wind_direction + '\'' +
                ", day_wind_power='" + day_wind_power + '\'' +
                ", max_degree='" + max_degree + '\'' +
                ", min_degree='" + min_degree + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
