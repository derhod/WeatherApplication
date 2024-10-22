package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

public class OpenWeatherApiClient {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    OpenWeatherApiClient() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode getJsonFromUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null) {
                // parse JSON string to JsonNode
                return objectMapper.readTree(response.body().string());
            } else {
                throw new IOException("Request failed: " + response.message());
            }

        }
    }

    // method to write JSON data to a file
    public void writeJsonToFile(JsonNode jsonData, String filePath) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonData);
    }

    // example method to demonstrate fetching data and saving it to a file
    public void fetchAndSaveData(String url, String filePath) throws IOException {
        JsonNode jsonData = getJsonFromUrl(url);
        writeJsonToFile(jsonData, filePath);
    }
}
