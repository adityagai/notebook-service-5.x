package com.notebook;

import com.notebook.api.Notebook;
import com.notebook.db.NotebookDAO;
import com.notebook.health.DatabaseHealthCheck;
import com.notebook.resources.NotebookResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class NotebookApplication extends Application<NotebookConfiguration> {

    private final HibernateBundle<NotebookConfiguration> hibernateBundle =
            new HibernateBundle<NotebookConfiguration>(Notebook.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(NotebookConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(String[] args) throws Exception {
        new NotebookApplication().run(args);
    }

    @Override
    public String getName() {
        return "notebook-service";
    }

    @Override
    public void initialize(Bootstrap<NotebookConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(NotebookConfiguration configuration, Environment environment) {
        final NotebookDAO dao = new NotebookDAO(hibernateBundle.getSessionFactory());
        final NotebookResource resource = new NotebookResource(dao);
        final DatabaseHealthCheck healthCheck = new DatabaseHealthCheck(hibernateBundle.getSessionFactory());

        environment.jersey().register(resource);
        environment.healthChecks().register("database", healthCheck);
    }
}
