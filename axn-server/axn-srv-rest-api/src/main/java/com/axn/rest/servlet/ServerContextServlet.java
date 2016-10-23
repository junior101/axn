package com.axn.rest.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Servlet for deploy and undeploy action
 */
@Slf4j
@WebListener
public final class ServerContextServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("Module ::axn-srv-rest-api:: is deployed successfully");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // This manually deregisters JDBC driver
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.info(String.format("Deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                log.info(String.format("Error when deregistering jdbc driver: %s", driver));
            }
        }
        log.info("Module ::axn-srv-rest-api:: is undeployed successfully");
    }
}
