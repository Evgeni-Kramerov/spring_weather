package org.ek.weather.repository;

import org.ek.weather.config.TestDataConfig;
import org.ek.weather.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDataConfig.class)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByLogin() {
        User user = new User("login", "password");
        userRepository.save(user);
        User userFromRepository = userRepository.findByLogin("login").orElse(null);
        System.out.println(userFromRepository);
        Assertions.assertEquals("login", userFromRepository.getLogin());

    }
}