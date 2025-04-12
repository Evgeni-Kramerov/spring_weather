package org.ek.weather.repository;

import org.ek.weather.config.TestWebConfig;
import org.ek.weather.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,    })
@ContextConfiguration(classes = WebConfig.class, loader = AnnotationConfigContextLoader.class)
class UserRepositoryTest {

    @Test
    void findByLogin() {

        System.out.println("findByLogin test");
    }
}