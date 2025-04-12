package org.ek.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.dto.LocationResponseDTO;
import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.dto.WeatherResponseDTO;
import org.ek.weather.exception.InvalidPasswordException;
import org.ek.weather.exception.PasswordsDoesntMatchException;
import org.ek.weather.exception.UserAlreadyExistException;
import org.ek.weather.exception.UserNotFoundException;
import org.ek.weather.http_api.OpenweatherAPI;
import org.ek.weather.model.Location;
import org.ek.weather.model.Session;
import org.ek.weather.model.User;
import org.ek.weather.service.LocationService;
import org.ek.weather.service.SessionService;
import org.ek.weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class WeatherController {

    private final UserService userService;
    private final SessionService sessionService;
    private final LocationService locationService;
    private final OpenweatherAPI openweatherAPI;


    @Autowired
    WeatherController(UserService userService,
                      SessionService sessionService,
                      LocationService locationService,
                      OpenweatherAPI openweatherAPI) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.locationService = locationService;
        this.openweatherAPI = openweatherAPI;
    }

    @GetMapping("/")
    public String MainPage(HttpServletRequest request, Model model) throws URISyntaxException, IOException, InterruptedException {

        Cookie sessionId = WebUtils.getCookie(request, "_sessionId");
        //if == null - redirect to authentication
        if (sessionId == null) {
            return "redirect:/login";
        }
        else{
            //TODO exception handling
            Session session = sessionService.findById(UUID.fromString(sessionId.getValue())).orElse(null);
            User user = userService.findById(session.getUser().getId()).orElse(null);
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
        }
        return "index";
    }

    //*Delete city

    @PostMapping("/delete_city")
    public String deleteCity(@ModelAttribute("locationToDelete") String locationToDelete,
            HttpServletRequest request) {
        Cookie sessionId = WebUtils.getCookie(request, "_sessionId");
        //if == null - redirect to authentication
        if (sessionId == null) {
            return "redirect:/login";
        }
        else {
            //TODO exception handling
            Session session = sessionService.findById(UUID.fromString(sessionId.getValue())).orElse(null);
            User user = userService.findById(session.getUser().getId()).orElse(null);
            locationService.deleteLocation(locationToDelete, user);
        }

        return "redirect:/";


    }

    //*  Adding city
    @PostMapping("/add_city")
    public String postAddCity(@ModelAttribute("name") String name,
            @ModelAttribute("latitude") double lattitude,
            @ModelAttribute("longitude") double longitude,
            HttpServletRequest request, Model model) {

        Cookie sessionId = WebUtils.getCookie(request, "_sessionId");
        //if == null - redirect to authentication
        if (sessionId == null) {
            return "redirect:/login";
        }
        else{
            //TODO exception handling
            Session session = sessionService.findById(UUID.fromString(sessionId.getValue())).orElse(null);
            User user = userService.findById(session.getUser().getId()).orElse(null);
            model.addAttribute("username", user.getLogin());

            Location location = new Location(name,user,lattitude,longitude);
            locationService.addNewLocation(location);
        }
        return  "redirect:/";
    }

    //*Search*//
    //*

    @GetMapping("/search")
    public String Search(HttpServletRequest request, Model model) {
        Cookie sessionId = WebUtils.getCookie(request, "_sessionId");
        //if == null - redirect to authentication
        if (sessionId == null) {
            return "redirect:/login";
        }
        else{
            //TODO exception handling
            Session session = sessionService.findById(UUID.fromString(sessionId.getValue())).orElse(null);
            User user = userService.findById(session.getUser().getId()).orElse(null);
            model.addAttribute("username", user.getLogin());
            return "search-results";
        }
    }

    @PostMapping("/search")
    public String postSearch(@ModelAttribute("search-input") String cityName,
                             RedirectAttributes redirectAttributes){
        try {
            LocationResponseDTO[] locationResponseDTOS = openweatherAPI.getLocations(cityName);
            redirectAttributes.addFlashAttribute("locations", locationResponseDTOS);
            return "redirect:/search";
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //*AUTEntification*//
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie sessionId = WebUtils.getCookie(request, "_sessionId");
        if (!(sessionId == null)) {
            sessionId.setMaxAge(0);
        }
        response.addCookie(sessionId);
        return "redirect:/";
    }



    @GetMapping("/login")
    public String getLoginPage() {return "sign-in";}

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("username") String username,
                            @ModelAttribute("password") String password,
                            HttpServletResponse response){
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO(
                username, password);

        User autentificatedUser = userService.authenticateUser(authenticationRequestDTO);
            //write session to db
         UUID sessionId =  sessionService.createNewSession(autentificatedUser);
            //make cookies with this session uuid "_sessionId"
        Cookie sessionIdCookie = new Cookie("_sessionId", sessionId.toString());
         sessionIdCookie.setMaxAge(60 * 30);
            //TODO Model VS response
         response.addCookie(sessionIdCookie);

         return "redirect:/";

    }

    //*REGISTRATION*//

    @GetMapping("/new")
    public String getSignUp() {return "sign-up";}


    @PostMapping("/new")
    public String postSignUp(@ModelAttribute("username") String username,
                         @ModelAttribute("password") String password,
                         @ModelAttribute("repeat-password") String repeatPassword) {

        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO(
                username, password, repeatPassword);

        userService.validateUser(registrationRequestDTO);
        userService.addNewUser(registrationRequestDTO);

        return "sign-in";
    }

}
