package org.ek.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ek.weather.dto.WeatherResponseDTO;
import org.ek.weather.http_api.OpenweatherAPI;
import org.ek.weather.model.Location;
import org.ek.weather.model.User;
import org.ek.weather.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final LocationService locationService;
    private final OpenweatherAPI openweatherAPI;


    @Autowired
    public MainController(LocationService locationService,
                          OpenweatherAPI openweatherAPI) {
        this.locationService = locationService;
        this.openweatherAPI = openweatherAPI;
    }

    @GetMapping("/")
    public String MainPage(HttpServletRequest request,
                           Model model) throws URISyntaxException, IOException, InterruptedException {

        User user = (User) request.getAttribute("_user_attribute");

        List<Location> baseLocations = locationService.getAllUserLocations(user);
        List<WeatherResponseDTO> locations = new ArrayList<>();

        for (Location location : baseLocations) {
            WeatherResponseDTO locationResponseDTO = openweatherAPI
                    .getWeather(location.getLatitude(),
                                location.getLongitude(),
                                location.getName());
            locations.add(locationResponseDTO);
        }
        model.addAttribute("username", user.getLogin());
        model.addAttribute("locations", locations);

        return "index";
    }

    //*Delete city

    @PostMapping("/delete_city")
    public String deleteCity(@ModelAttribute("locationToDelete") String locationToDelete,
            HttpServletRequest request) {

        User user = (User) request.getAttribute("_user_attribute");

        locationService.deleteLocation(locationToDelete, user);

        return "redirect:/";

    }

    //*  Adding city
    @PostMapping("/add_city")
    public String postAddCity(@ModelAttribute("name") String name,
            @ModelAttribute("latitude") double lattitude,
            @ModelAttribute("longitude") double longitude,
            HttpServletRequest request, Model model) {

        User user = (User) request.getAttribute("_user_attribute");
        model.addAttribute("username", user.getLogin());

        Location location = new Location(name,user,lattitude,longitude);
        locationService.addNewLocation(location);

        return  "redirect:/";
    }



}
