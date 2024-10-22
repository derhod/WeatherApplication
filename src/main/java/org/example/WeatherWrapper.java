package org.example;

import java.util.List;

public class WeatherWrapper {
    private List<WeatherObject> weatherObjectList;
    // Creating a Wrapper for the list objects inorder to pretty print them from an array to a .json
    public WeatherWrapper(List<WeatherObject> weatherObjectList) {
        this.weatherObjectList = weatherObjectList;
    }

    public List<WeatherObject> getWeatherObjectList() {
        return weatherObjectList;
    }
    public void setWeatherObjectList(List<WeatherObject> weatherObjectList) {
        this.weatherObjectList = weatherObjectList;
    }
}
