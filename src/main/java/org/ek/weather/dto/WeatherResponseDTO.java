package org.ek.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponseDTO {
    private String name;
    private String country;
    private int temp;
    private int feelsLike;
    private String mainWeather;
    private String icon;
    private int humidity;
}
