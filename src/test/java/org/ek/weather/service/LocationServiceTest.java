package org.ek.weather.service;

import org.ek.weather.model.Location;
import org.ek.weather.model.User;
import org.ek.weather.repository.LocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location testLocation;
    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User();
        testUser.setId(1);
        testUser.setLogin("john");

        testLocation = new Location();
        testLocation.setId(1);
        testLocation.setName("New York");
        testLocation.setUser(testUser);
    }

    @Test
    public void addNewLocation_onSuccessAddsNewLocation() {
        locationService.addNewLocation(testLocation);

        verify(locationRepository, times(1)).save(testLocation);
    }

    @Test
    public void getAllUserLocations_onSuccessReturnAllUserLocations() {
        List<Location> expectedLocations = Collections.singletonList(testLocation);

        when(locationRepository.findByUser(testUser)).thenReturn(expectedLocations);

        List<Location> actual = locationService.getAllUserLocations(testUser);

        Assertions.assertEquals(expectedLocations, actual);
    }

    @Test
    void deleteLocation_shouldFindAndDeleteLocation() {
        when(locationRepository.findByUserAndName(testUser, "New York")).thenReturn(testLocation);

        locationService.deleteLocation("New York", testUser);

        verify(locationRepository).findByUserAndName(testUser, "New York");
        verify(locationRepository).delete(testLocation);
    }
}
