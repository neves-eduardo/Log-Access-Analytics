package com.neves_eduardo.logaccesssanalytics.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();
        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }
}
