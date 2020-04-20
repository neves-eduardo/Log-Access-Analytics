package com.neves_eduardo.logaccesssanalytics.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;


public class JettyServer{
    private ServletContext servletContext;
    public Server createServer(int port, WebApplicationContext applicationContext)
    {
        Server server = new Server(port);
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(applicationContext)), "/");
        contextHandler.addEventListener(new ContextLoaderListener(applicationContext));
        server.setHandler(contextHandler);
        return server;
    }

}
