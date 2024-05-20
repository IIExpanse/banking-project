package ru.expanse.repository;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import ru.expanse.entity.User;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    public Uni<List<User>> filterUsersByNameAndEmail(String name, String email) {
        PanacheQuery<User> query = this.findAll();
        if (name != null) {
            query = query.filter("nameFilter", Parameters.with("searchName", name));
        }
        if (email != null) {
            query = query.filter("emailFilter", Parameters.with("searchEmail", email));
        }
        return query.list();
    }
}
