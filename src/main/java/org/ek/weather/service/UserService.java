package org.ek.weather.service;

import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.exception.InvalidPasswordException;
import org.ek.weather.exception.PasswordsDoesntMatchException;
import org.ek.weather.exception.UserAlreadyExistException;
import org.ek.weather.exception.UserNotFoundException;
import org.ek.weather.model.User;
import org.ek.weather.repository.UserRepository;
import org.ek.weather.utils.mapper.UserMapper;
import org.ek.weather.utils.password.PassHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PassHasher passHasher;

    @Autowired
    public UserService(UserRepository userRepository, PassHasher passHasher) {
        this.userRepository = userRepository;
        this.passHasher = passHasher;
    }

    public void addNewUser(RegistrationRequestDTO registrationRequestDTO) {
        //hash password
        passHasher.hashPassword(registrationRequestDTO);
        //create user
        User user = UserMapper.INSTANCE.registrationRequestDTOToUser(registrationRequestDTO);
        //save to DB
        userRepository.save(user);
    }

    public User authenticateUser(AuthenticationRequestDTO authenticationRequestDTO) {
        Optional<User> authentificatedUser =  userRepository.findByLogin(
                authenticationRequestDTO.getLogin());

        if (authentificatedUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User authenticatedUser = authentificatedUser.get();
        if (!passHasher.verifyPassword(authenticationRequestDTO,authenticatedUser)) {
            throw new InvalidPasswordException("Incorrect password");
        }

        return authenticatedUser;
    }


    public void validateUser(RegistrationRequestDTO registrationRequestDTO) {
        if (!registrationRequestDTO.getPassword()
                .equals(registrationRequestDTO.getRepeatPassword())) {
            throw new PasswordsDoesntMatchException("Passwords doesnt match");
        }

        if ((userRepository.findByLogin(registrationRequestDTO.getLogin()).isPresent())) {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
