package org.ek.weather.config;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRegistration;
import org.ek.weather.filter.AutentificationFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebDispatcherServletInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("enableLoggingRequestDetails", "true");
    }


    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                            new AutentificationFilter(),
                            };
    }


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
