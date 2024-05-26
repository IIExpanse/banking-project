package ru.expanse.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FilterDefs({
        @FilterDef(name = "nameFilter", parameters = @ParamDef(name = "searchName", type = String.class)),
        @FilterDef(name = "emailFilter", parameters = @ParamDef(name = "searchEmail", type = String.class))
})
@Filters({
        @Filter(name = "nameFilter", condition = "LOWER(name) LIKE CONCAT(LOWER(:searchName), '%')"),
        @Filter(name = "emailFilter", condition = "LOWER(email) LIKE CONCAT(LOWER(:searchEmail), '%')")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "birth_date", nullable = false)
    private Timestamp birthDate;
}