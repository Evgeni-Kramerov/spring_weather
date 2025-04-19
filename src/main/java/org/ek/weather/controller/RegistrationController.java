package org.ek.weather.controller;

import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

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
