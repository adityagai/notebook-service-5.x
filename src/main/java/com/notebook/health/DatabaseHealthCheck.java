package com.notebook.health;

import com.codahale.metrics.health.HealthCheck;
import org.hibernate.SessionFactory;

public class DatabaseHealthCheck extends HealthCheck {

    private final SessionFactory sessionFactory;

    public DatabaseHealthCheck(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Result check() {
        try {
            sessionFactory.openSession()
                    .createNativeQuery("SELECT 1", Integer.class)
                    .getSingleResult();
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("Database unreachable: " + e.getMessage());
        }
    }
}
