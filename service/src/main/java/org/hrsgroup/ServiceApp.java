package org.hrsgroup;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.logging.Logger;

@ApplicationPath("/v1")
@ApplicationScoped
public class ServiceApp extends Application {
    private static final Logger log = Logger.getLogger(ServiceApp.class.getName());

    public void init(@Observes @Initialized(ApplicationScoped.class) Object context) {
        log.info("Type of context: " + context.getClass().getTypeName());
    }
}