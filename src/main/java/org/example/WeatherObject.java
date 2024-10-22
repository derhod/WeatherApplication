package org.example;

public class WeatherObject {

    private String city;
    private double temperature;
    private String humidity;
    private String conditions;

    public WeatherObject(){}
    // Weather Object to build all the inputs from the weather from individual cities
    public WeatherObject(String city, double temperature, String humidity, String conditions) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.conditions = conditions;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }






}
