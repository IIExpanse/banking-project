package ru.expanse.repository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.VertxContextSupport;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import ru.expanse.entity.User;
import ru.expanse.utils.ObjectFactory;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserRepositoryTest {
    @Inject
    UserRepository userRepository;

    @Test
    void addUser() throws Throwable {
        User user = VertxContextSupport.subscribeAndAwait(
                () -> Panache.withTransaction(
                        () -> userRepository.persist(ObjectFactory.getDefaultUser())
                )
        );
        User returnedUser2 = VertxContextSupport.subscribeAndAwait(
                () -> Panache.withSession(
                        () -> userRepository.findById(user.getId())
                )
        );
        assertEquals(user.getId(), returnedUser2.getId());
    }

    @Test
    void updateUser() {

    }

    @Test
    void deleteUser() {

    }


}