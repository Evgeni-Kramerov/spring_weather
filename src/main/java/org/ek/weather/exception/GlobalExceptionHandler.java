package org.ek.weather.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidPasswordException.class})
    public String handleInvalidPassword(InvalidPasswordException e,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("passwordError", true);
        return "redirect:/login";
    }

    @ExceptionHandler({UserNotFoundException.class})
    public String handleInvalidUser(UserNotFoundException e,
                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginError", true);
        return "redirect:/login";
    }

    @ExceptionHandler({PasswordsDoesntMatchException.class})
    public String handlePasswordDoesntMatch(PasswordsDoesntMatchException e,
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("passwordDoesntMatchError", true);
        return "redirect:/new";
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    public String handleUserAlreadyExist(UserAlreadyExistException e,
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userAlreadyExistError", true);
        return "redirect:/new";
    }

}
