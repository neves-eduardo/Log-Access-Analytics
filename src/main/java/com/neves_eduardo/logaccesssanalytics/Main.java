package com.neves_eduardo.logaccesssanalytics;

import com.neves_eduardo.logaccesssanalytics.config.PropertiesReader;
import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.server.JettyServer;
import org.eclipse.jetty.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        JettyServer jettyServer = new JettyServer();
        applicationContext.setConfigLocation("com.neves_eduardo.logaccesssanalytics.config.AppConfig");
        PropertiesReader propertiesReader = new PropertiesReader();
        Server server = jettyServer.createServer(Integer.parseInt(propertiesReader.loadProperty("app.port")),applicationContext);
        server.start();
        server.join();
    }

}
