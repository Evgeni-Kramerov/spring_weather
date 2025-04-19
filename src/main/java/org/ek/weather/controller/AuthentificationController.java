package org.ek.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.model.User;
import org.ek.weather.service.SessionService;
import org.ek.weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

@Controller
public class AuthentificationController {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public AuthentificationController(UserService userService,
                                      SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
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

}
