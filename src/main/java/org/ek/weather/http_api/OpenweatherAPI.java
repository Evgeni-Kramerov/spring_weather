package org.ek.weather.http_api;

import org.ek.weather.dto.LocationResponseDTO;
import org.ek.weather.dto.WeatherResponseDTO;
import org.ek.weather.utils.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Component
public class OpenweatherAPI {

    private final HttpClient httpClient;
    private final JsonMapper jsonMapper;

    private final String API_KEY = System.getenv("WEATHER_API_KEY");

    private final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q={city name}&limit=5&appid={API key}";
    private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&units=metric&appid={API key}";

    @Autowired
    public OpenweatherAPI(JsonMapper jsonMapper) {
        httpClient = HttpClient.newBuilder().build();
        this.jsonMapper = jsonMapper;
    }

    public WeatherResponseDTO getWeather(double lat, double lon, String locationName) throws URISyntaxException, IOException, InterruptedException {
        String uri = getWeatherRequestURL(lat, lon);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return jsonMapper.getWeather(response.body(), locationName);
    }

    public LocationResponseDTO[] getLocations(String cityName) throws URISyntaxException, IOException, InterruptedException {

        String uri = getCoordinatesRequestURL(cityName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return jsonMapper.getLocations(response.body());

    }

    private String getCoordinatesRequestURL(String cityName) {
        return GEOCODING_API_URL
                .replace("{city name}",cityName.trim().replace(" ","%20"))
                .replace("{API key}", API_KEY);
    }

    private String getWeatherRequestURL(Double latitude, Double longitude) {
        return WEATHER_API_URL
                .replace("{lat}",String.valueOf(latitude))
                .replace("{lon}",String.valueOf(longitude))
                .replace("{API key}", API_KEY);
    }

}
