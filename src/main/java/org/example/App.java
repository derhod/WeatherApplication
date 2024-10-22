package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class App 
{
    static File jsonPathFile = new File(EnumValues.FINALFILE.getValue());
    static public String apiKey = EnumValues.APIKEY.getValue();

    public static void main( String[] args ) throws IOException {
        addCityToList();

    }

    public static void addCityToList() throws IOException {
        System.out.println("Please enter the name of a city for which you require a weather report: \n");
        CreateWeatherListCities list = new CreateWeatherListCities();

        while(true) {
            OpenWeatherApiClient client = new OpenWeatherApiClient();

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            //Selection options
            if(input.equals("JSON")) {
                jsonPathManipulation();
                break;

            } if(input.equals("populate")) {
                populateFullList();
            }

            // API and File Path
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + input + "&appid=" + apiKey + "&units=metric";
            String filePath = EnumValues.FILEELEMENT.getValue();

            try{
                // fetch and save
                client.fetchAndSaveData(url, filePath);
                System.out.println("Data successfully saved to " + filePath);


                client.fetchAndSaveData(url, filePath);

                list.createListCities(filePath);

                for (Map.Entry<Long, WeatherObject> entry : list.getWeatherObjects().entrySet()) {
                    System.out.println("Index: " + entry.getKey() + " - " + entry.getValue());
                }

                list.writeWeatherDataToJson(EnumValues.FINALFILE.getValue());


            } catch (IOException e){
                //e.printStackTrace();
                System.out.println("An error occurred while fetching or saving data");
                System.out.println("Enter in the name of a city or check your spelling.");


            }
        }
    }
    public static void jsonPathManipulation() throws IOException {
        boolean run = true;
        while(true){
            try{
                /*
                    Query ex: $.weatherObjectList[*].city
                    Query ex: $.weatherObjectList[?(@.city == 'Toronto')].conditions
                    Get a Subset of an Array: $.weatherObjectList[0:2]
                    Combine Filters with Fields: $.weatherObjectList[?(@.temperature > 15)].city
                    Number of Items in an Array: length($.weatherObjectList)
                    Sort and Limit Queries: $.weatherObjectList[?(@.temperature)].@sort()
                    Limit Results: $.weatherObjectList[0:1]
                    Get value for specific key: $.weatherObjectList[?(@.city == 'London')].humidity
                 */

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter a JSON Path Query in order to interact with City Weather list data: ");
                String JSONPathQuery = scanner.nextLine();

                //Selections for return to main, stop the app.
                if(JSONPathQuery.equals("main")) {
                    addCityToList();
                    break;
                } else if (JSONPathQuery.equals("stop")) {
                   break;


                } else if(true) {
                    List<String> query = JsonPath.read(jsonPathFile, JSONPathQuery);
                    System.out.println("Query Results: " + query);
                }
            } catch (Exception e){
                System.out.println("An Error occurred, that's ok");
            }
        }
    }
    public static void populateFullList() throws IOException {
        // Create a list populated with upper bound of allowable API calls 50
        String dateipfad = "./cities_list.txt";

        CreateWeatherListCities list = new CreateWeatherListCities();
        OpenWeatherApiClient client = new OpenWeatherApiClient();

        // using a buffered reader to write from text to WeatherObject
        try(BufferedReader br = new BufferedReader(new FileReader(dateipfad))){
            String number;
            // reads the 50 cities from a text file "cities_list.txt and adds to an overall JSON list of cities
            while ((number = br.readLine()) != null) {
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + number + "&appid=" + apiKey + "&units=metric";

                String filePath ="weatherData.json";
                try{
                    client.fetchAndSaveData(url, filePath);
                    System.out.println("Data successfully saved to " + filePath);

                    client.fetchAndSaveData(url, filePath);

                    list.createListCities(filePath);

                    for (Map.Entry<Long, WeatherObject> entry : list.getWeatherObjects().entrySet()) {
                        System.out.println("Index: " + entry.getKey() + " - " + entry.getValue());
                    }

                    list.writeWeatherDataToJson(EnumValues.FINALFILE.getValue());

                } catch (IOException e){
                    //e.printStackTrace();
                    System.out.println("An error occurred while fetching or saving data");
                    System.out.println("Enter in the name of a city or check your spelling.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

