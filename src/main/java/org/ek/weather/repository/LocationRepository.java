package org.ek.weather.repository;

import org.ek.weather.model.Location;
import org.ek.weather.model.Session;
import org.ek.weather.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    List<Location> findByUser(User user);

}
