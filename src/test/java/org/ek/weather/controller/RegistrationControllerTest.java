package org.ek.weather.controller;

import org.ek.weather.config.TestDataConfig;
import org.ek.weather.config.TestWebConfig;
import org.ek.weather.model.User;
import org.ek.weather.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestWebConfig.class, TestDataConfig.class})
@ActiveProfiles("test")
@Transactional
public class RegistrationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .alwaysDo(print())
                .build();

        User user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        userRepository.save(user);


    }

    @Test
    public void postSingUp_throwException_whenUserAlreadyExists() throws Exception {
        mockMvc.perform(post("/new")
                        .param("username", "test_user")
                        .param("password", "pass")
                        .param("repeat-password", "pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/new"))
                .andExpect(flash().attributeExists("userAlreadyExistError"));
    }

    @Test
    public void postSingUp_throwException_whenPasswordsDontMatch() throws Exception {
        mockMvc.perform(post("/new")
                        .param("username", "test_user3")
                        .param("password", "pass123")
                        .param("repeat-password", "pass321"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/new"))
                .andExpect(flash().attributeExists("passwordDoesntMatchError"));
    }

    @Test
    public void postSignUp_shouldCreateUserInDB() throws Exception {

        mockMvc.perform(post("/new")
                .param("username", "test_user2")
                .param("password", "pass")
                .param("repeat-password", "pass"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in"));

        Optional<User> savedUser = userRepository.findByLogin("test_user2");

        assertTrue(savedUser.isPresent(),"USer should be saved to database");
        assertEquals("test_user2",savedUser.get().getLogin());
        assertNotNull(savedUser.get().getPassword(),"Password should not be null");

    }

    @Test
    public void getSignUp_shouldReturnSignUpView() throws Exception {

            mockMvc.perform(get("/new"))
                   .andExpectAll(status().isOk())
                   .andExpect(view().name("sign-up"));

    }


}
