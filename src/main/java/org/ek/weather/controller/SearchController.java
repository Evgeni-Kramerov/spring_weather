package org.ek.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ek.weather.dto.LocationResponseDTO;
import org.ek.weather.http_api.OpenweatherAPI;
import org.ek.weather.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class SearchController {

    private final OpenweatherAPI openweatherAPI;

    @Autowired
    public SearchController(OpenweatherAPI openweatherAPI) {
        this.openweatherAPI = openweatherAPI;
    }

    @GetMapping("/search")
    public String Search(HttpServletRequest request, Model model) {

        User user = (User) request.getAttribute("_user_attribute");
        model.addAttribute("username", user.getLogin());
        return "search-results";

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


}
