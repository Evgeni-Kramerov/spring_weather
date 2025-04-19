package org.ek.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.ek.weather.model.Location;
import org.ek.weather.model.User;
import org.ek.weather.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addNewLocation(Location location) {
        log.info("Adding new location: {}", location);
        locationRepository.save(location);
    }

    public List<Location> getAllUserLocations(User user) {
        return locationRepository.findByUser(user);
    }

    public void deleteLocation(String locationName, User user) {
        Location locationToDelete = locationRepository.findByUserAndName(user, locationName);
        locationRepository.delete(locationToDelete);
    }

}
