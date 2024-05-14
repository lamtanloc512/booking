package org.hrsgroup;


import com.github.javafaker.Faker;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import org.hrsgroup.model.Address;
import org.hrsgroup.model.Hotel;
import org.hrsgroup.model.Room;
import org.hrsgroup.model.User;

import java.sql.SQLException;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@ApplicationPath("/v1")
@ApplicationScoped
@LoginConfig(authMethod = "MP-JWT")
public class ServiceApp extends Application {
    private static final Logger log = Logger.getLogger(ServiceApp.class.getName());

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;


    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object context) throws SQLException {

        // auto gendata for testing
        Faker faker = new Faker();
        IntStream.rangeClosed(0, 10).forEach(x -> {
            Address address = new Address(faker.address().streetName(),
                    faker.address().streetAddressNumber()
            );
            em.persist(address);

            User user = new User(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone()
            );
            em.persist(user);
            Set<Room> rooms = IntStream.rangeClosed(0, 5)
                    .mapToObj(y -> {
                        Room room = new Room(
                                faker.phoneNumber().cellPhone(),
                                faker.random().nextInt(5),
                                (float) faker.random().nextInt(5),
                                (float) faker.random().nextInt(5),
                                faker.random().nextDouble());
                        em.persist(room);
                        return room;
                    })
                    .collect(Collectors.toSet());

            Hotel hotel = new Hotel(
                    faker.name().fullName(),
                    address,
                    faker.phoneNumber().cellPhone(),
                    rooms
            );
            em.persist(hotel);
        });
        log.info("Service Ready!");
    }
}