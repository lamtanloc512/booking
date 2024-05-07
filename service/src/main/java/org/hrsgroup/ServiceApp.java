package org.hrsgroup;


import com.github.javafaker.Faker;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.logging.Logger;
import java.util.stream.IntStream;

@ApplicationPath("/v1")
@ApplicationScoped
public class ServiceApp extends Application {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    private static final Logger log = Logger.getLogger(ServiceApp.class.getName());

//    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object context) {
//        Faker faker = new Faker();
//        IntStream.rangeClosed(0, 10).forEach(x -> {
//            Address address = new Address(faker.address().streetName(),
//                    faker.address().streetAddressNumber()
//            );
//            em.persist(address);
//        });

    }
}