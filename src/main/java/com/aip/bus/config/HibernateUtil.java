package com.aip.bus.config;

import com.aip.bus.model.Booking;
import com.aip.bus.model.Bus;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    private static final SessionFactory FACTORY = buildFactory();
    private HibernateUtil() { }

    private static SessionFactory buildFactory() {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/bus_reservation?useSSL=false&serverTimezone=UTC");
        cfg.setProperty("hibernate.connection.username", "root");
        cfg.setProperty("hibernate.connection.password", "root123"); // Change this.
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.format_sql", "true");
        cfg.setProperty("hibernate.hbm2ddl.auto", "validate");
        cfg.addAnnotatedClass(Bus.class);
        cfg.addAnnotatedClass(Booking.class);
        return cfg.buildSessionFactory();
    }
    public static SessionFactory getFactory() { return FACTORY; }
}
