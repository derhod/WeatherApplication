package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class CreateWeatherListCities {


    private long indexWeatherList = 1;

    private final Map<Long, WeatherObject> weatherObjects = new HashMap<>();

    public void createListCities(String cityWeather) throws IOException {
        String filePath = cityWeather;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            //Query
            String city = rootNode.path("name").asText();
            double temperature = rootNode.path("main").path("temp").asDouble();
            String humidity = rootNode.path("main").path("humidity").asText();

            // Accessing the part of the JSON Array object for Conditions
            String conditions="";
            JsonNode weatherArray = rootNode.path("weather");
            JsonNode weatherNode = weatherArray.get(0);
            if (weatherArray.isArray() && weatherArray.size() > 0) {
               conditions = weatherNode.path("description").asText();
            }

            // Use Weather Object to populate the object fields
            WeatherObject weatherObject = new WeatherObject();
            weatherObject.setCity(city);
            weatherObject.setTemperature(temperature);
            weatherObject.setHumidity(humidity);
            weatherObject.setConditions(conditions);

            // adds the Weather Object to the HashMaps
            weatherObjects.put(indexWeatherList++, weatherObject);

            System.out.println("Here is your requested weather report: \n");

            System.out.println("Requested City: " + city);
            System.out.println("Temperature (Celsius): " + temperature);
            System.out.println("Weather Description: " + conditions);
            System.out.println("Humidity: " + humidity);

            // Simple user friendly output
            String mainCondition = weatherNode.path("main").asText();
            switch (mainCondition) {
                case "Clouds": System.out.println("You won't be seeing much Sun today \n"); break;
                case "Thunderstorm": System.out.println("Consider staying in-doors today \n"); break;
                case "Drizzle": System.out.println("Make sure to take a umbrella \n"); break;
                case "Rain": System.out.println("Make sure to take a umbrella \n"); break;
                default: System.out.println("Have a good day \n"); break;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error in the create weather list class");
        }
        System.out.println("size of hash map: " + weatherObjects.size());
    }

    public Map<Long, WeatherObject> getWeatherObjects() {
        return weatherObjects;
    }

    public void writeWeatherDataToJson(String outputFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Wrapper class to consolidate all the object data into a structured list object
        WeatherWrapper wrapper = new WeatherWrapper(new ArrayList<>(weatherObjects.values()));

        // writing to the final file path with the wrapper as input
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), wrapper);
    }
}


