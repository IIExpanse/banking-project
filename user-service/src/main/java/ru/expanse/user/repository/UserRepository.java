package ru.expanse.user.repository;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import ru.expanse.user.entity.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    public Uni<List<User>> getFilteredUsers(
            String name,
            String email,
            Timestamp startBirthDate,
            Timestamp endBirthDate) {
        PanacheQuery<User> query = this.findAll();
        if (name != null) {
            query = query.filter("nameFilter", Parameters.with("searchName", name));
        }
        if (email != null) {
            query = query.filter("emailFilter", Parameters.with("searchEmail", email));
        }
        if (startBirthDate != null || endBirthDate != null) {
            startBirthDate = startBirthDate == null ? Timestamp.from(Instant.MIN) : startBirthDate;
            endBirthDate = endBirthDate == null ? Timestamp.from(Instant.MAX) : endBirthDate;

            query = query.filter("birthDateFilter",
                    Parameters.with("startBirthDate", startBirthDate).and("endBirthDate", endBirthDate));
        }
        return query.list();
    }
}
