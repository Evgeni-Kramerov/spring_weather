package org.ek.weather.repository;

import org.ek.weather.model.Session;
import org.ek.weather.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    Optional<Session> findById(UUID id);

}
