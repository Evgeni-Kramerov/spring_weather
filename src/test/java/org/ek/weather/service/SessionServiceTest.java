package org.ek.weather.service;

import org.ek.weather.model.Session;
import org.ek.weather.model.User;
import org.ek.weather.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    public void createNewSession_shouldReturnSessionUUIDOnSuccess() {
        Session session = new Session();
        session.setId(UUID.randomUUID());

        when(sessionRepository.save(Mockito.any(Session.class))).thenReturn(session);

        assertEquals(session.getId(), sessionService.createNewSession(new User()));
    }

    @Test
    public void findById_shouldReturnSessionOnSuccess() {
        Session session = new Session();
        session.setId(UUID.randomUUID());

        when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));

        assertTrue(sessionService.findById(session.getId()).isPresent());
        assertEquals(session, sessionService.findById(session.getId()).get());
    }
}
