package ru.expanse.repository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.VertxContextSupport;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.expanse.entity.User;
import ru.expanse.utils.ObjectFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserRepositoryTest {
    @Inject
    UserRepository userRepository;

    @BeforeEach
    void clearDatabase() throws Throwable {
        VertxContextSupport.subscribeAndAwait(
                () -> Panache.withTransaction(
                        () -> userRepository.deleteAll()
                )
        );
    }

    @Test
    void addUser() throws Throwable {
        User user = saveUser(ObjectFactory.getDefaultUser());
        User returnedUser2 = findUser(user.getId());
        assertEquals(user.getId(), returnedUser2.getId());
    }

    @Test
    void updateUser() throws Throwable {
        User savedUser = saveUser(ObjectFactory.getDefaultUser());

        VertxContextSupport.subscribeAndAwait(
                () -> Panache.withTransaction(
                        () -> userRepository.findById(savedUser.getId())
                                .onItem().call(user -> {
                                    user.setName("new name");
                                    return Uni.createFrom().item(user);
                                })
                                .onItem().call(userRepository::persist)
                )
        );

        User returnedUser = findUser(savedUser.getId());

        assertEquals("new name", returnedUser.getName());
    }

    @Test
    void deleteUser() throws Throwable {
        User user = saveUser(ObjectFactory.getDefaultUser());

        VertxContextSupport.subscribeAndAwait(
                () -> Panache.withTransaction(
                        () -> userRepository.deleteById(user.getId())
                )
        );
        assertNull(findUser(user.getId()));
    }

    private User saveUser(User user) throws Throwable {
        return VertxContextSupport.subscribeAndAwait(
                () -> Panache.withTransaction(
                        () -> userRepository.persist(user)
                )
        );
    }

    private User findUser(UUID id) throws Throwable {
        return VertxContextSupport.subscribeAndAwait(
                () -> Panache.withSession(
                        () -> userRepository.findById(id)
                )
        );
    }
}