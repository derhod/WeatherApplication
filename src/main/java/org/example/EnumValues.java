package org.example;

public enum EnumValues {
    // Enums for defensive programming practice to hide sensitive data.
    APIKEY("*********************************"),
    FINALFILE("finalWeatherData.json"),
    FILEELEMENT("weatherData.json");


    private final String value;

    EnumValues(String value) {
        this.value = value;
    }

    public String getValue(){return value;}
}
