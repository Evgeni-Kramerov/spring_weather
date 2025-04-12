package org.ek.weather.service;

import org.ek.weather.dto.AuthenticationRequestDTO;
import org.ek.weather.dto.RegistrationRequestDTO;
import org.ek.weather.exception.InvalidPasswordException;
import org.ek.weather.exception.PasswordsDoesntMatchException;
import org.ek.weather.exception.UserAlreadyExistException;
import org.ek.weather.exception.UserNotFoundException;
import org.ek.weather.model.Location;
import org.ek.weather.model.User;
import org.ek.weather.repository.LocationRepository;
import org.ek.weather.repository.UserRepository;
import org.ek.weather.utils.mapper.UserMapper;
import org.ek.weather.utils.password.PassHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addNewLocation(Location location) {
        locationRepository.save(location);
    }

    public List<Location> getAllUserLocations(User user) {
        return locationRepository.findByUser(user);
    }

}
