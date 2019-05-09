package com.bank.tcmb.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ApplicationProperties.readProperties();
        HibernateConfiguration.getSessionFactory();
        ApplicationLoader.loadApp();
        ApplicationLoader.addProxy();
    }

}
