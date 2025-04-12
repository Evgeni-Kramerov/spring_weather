package org.ek.weather.service;

import org.ek.weather.model.Session;
import org.ek.weather.model.User;
import org.ek.weather.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public UUID createNewSession(User user) {
        Session session = new Session(user, new Date(2025, Calendar.MAY,21));

        return sessionRepository.save(session).getId();
    }

    public Optional<Session> findById(UUID uuid) {
        return sessionRepository.findById(uuid);
    }
}
