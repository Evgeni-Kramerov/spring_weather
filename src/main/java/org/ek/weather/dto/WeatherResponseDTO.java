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
    private double temp;
    private double feelsLike;
    private String mainWeather;
    private String icon;
    private double humidity;
}
