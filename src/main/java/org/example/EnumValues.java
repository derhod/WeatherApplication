package org.example;

public enum EnumValues {
    // Enums for defensive programming practice to hide sensitive data.
    APIKEY("8ab9278b4129a0ef1a32ff470424a3cc"),
    FINALFILE("finalWeatherData.json"),
    FILEELEMENT("weatherData.json");


    private final String value;

    EnumValues(String value) {
        this.value = value;
    }

    public String getValue(){return value;}
}
