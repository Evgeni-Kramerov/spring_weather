package org.ek.weather.utils.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ek.weather.dto.LocationResponseDTO;
import org.ek.weather.dto.WeatherResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LocationResponseDTO[] getLocations(String jsonResponse) throws JsonProcessingException {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            LocationResponseDTO[] locationResponseDTOS = objectMapper
                    .readValue(jsonResponse, LocationResponseDTO[].class);
            return locationResponseDTOS;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public WeatherResponseDTO getWeather(String jsonResponse, String locationName) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            WeatherResponseDTO weatherResponseDTO = new WeatherResponseDTO();
//            private String name;
            weatherResponseDTO.setName(locationName);
//            private String country;
            weatherResponseDTO.setCountry(root.path("sys").path("country").asText());
//            private double temp;
            weatherResponseDTO.setTemp(root.path("main").path("temp").asInt());
            weatherResponseDTO.setFeelsLike(root.path("main").path("feels_like").asInt());
//            private String mainWeather;
            weatherResponseDTO.setMainWeather(root.get("weather").get(0).get("main").asText());
//            private String icon;
            weatherResponseDTO.setIcon(root.get("weather").get(0).get("icon").asText());
//            private double humidity;
            weatherResponseDTO.setHumidity(root.path("main").path("humidity").asInt(0));

            return weatherResponseDTO;

        }
        catch (Exception e) {
            throw e;
        }


    }
}
