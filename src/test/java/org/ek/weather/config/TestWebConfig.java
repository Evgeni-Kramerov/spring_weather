package org.ek.weather.config;

import org.ek.weather.controller.MainController;
import org.ek.weather.http_api.OpenweatherAPI;
import org.ek.weather.service.LocationService;
import org.ek.weather.service.SessionService;
import org.ek.weather.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
public class TestWebConfig implements WebMvcConfigurer {
    // ===Mocks===
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public SessionService sessionService() {
        return Mockito.mock(SessionService.class);
    }

    @Bean
    public LocationService locationService() {
        return Mockito.mock(LocationService.class);
    }

    @Bean
    public OpenweatherAPI openweatherAPI() {
        return Mockito.mock(OpenweatherAPI.class);
    }

    // ===Controller===
    @Bean
    public MainController weatherController() {
        return new MainController(locationService(), openweatherAPI());
    }

    // === Thymeleaf setup ===

    @Bean
    public SpringResourceTemplateResolver templateResolver(ApplicationContext ctx) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(ctx);
        resolver.setPrefix("classpath:/templates/");  // customize if needed
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ApplicationContext ctx) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver(ctx));
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    @Bean
    @Primary
    public ThymeleafViewResolver viewResolver(ApplicationContext ctx) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(ctx));
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

}
