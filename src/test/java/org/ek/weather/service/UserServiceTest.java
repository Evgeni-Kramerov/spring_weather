package org.ek.weather.service;

import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.exception.InvalidPasswordException;
import org.ek.weather.exception.PasswordsDoesntMatchException;
import org.ek.weather.exception.UserAlreadyExistException;
import org.ek.weather.exception.UserNotFoundException;
import org.ek.weather.model.User;
import org.ek.weather.repository.UserRepository;
import org.ek.weather.utils.password.PassHasher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PassHasher passHasher;

    @InjectMocks
    private UserService userService;

    @Test
    public void addNewUser_shouldHashPasswordAndSaveUser() {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("test", "pass", "pass");

        userService.addNewUser(registrationRequestDTO);

        verify(passHasher).hashPassword(any(RegistrationRequestDTO.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void authenticateUser_shouldReturnUserOnSuccess() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO("test", "pass");

        User user = new User();
        user.setLogin("test");

        when(userRepository.findByLogin("test")).thenReturn(Optional.of(user));
        when(passHasher.verifyPassword(authenticationRequestDTO, user)).thenReturn(true);

        User result = userService.authenticateUser(authenticationRequestDTO);

        assertEquals("test", result.getLogin());
    }

    @Test
    public void authenticateUser_userNotFound_shouldThrowException() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO("test", "pass");

        when(userRepository.findByLogin("test")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.authenticateUser(authenticationRequestDTO));
    }

    @Test
    public void authenticateUser_wrongPassword_shouldThrowException() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO("test", "pass");

        User user = new User();
        user.setLogin("test");

        when(userRepository.findByLogin("test")).thenReturn(Optional.of(user));
        when(passHasher.verifyPassword(authenticationRequestDTO, user)).thenReturn(false);

        assertThrows(InvalidPasswordException.class,
                () -> userService.authenticateUser(authenticationRequestDTO));

    }

    @Test
    public void validateUser_passwordDoesntMatch_shouldThrowException() {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("test", "pass", "pass2");

        assertThrows(PasswordsDoesntMatchException.class,
                () -> userService.validateUser(registrationRequestDTO));
    }

    @Test
    public void validateUser_userAlreadyExists_shouldThrowException() {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("test", "pass", "pass");

        when(userRepository.findByLogin("test")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class,
                () -> userService.validateUser(registrationRequestDTO));
    }

    @Test
    public void findById_existingId_shouldReturnUser() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }
}

