package ru.expanse.user.entity;

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
        @FilterDef(name = "emailFilter", parameters = @ParamDef(name = "searchEmail", type = String.class)),
        @FilterDef(name = "birthDateFilter",
                parameters = {
                        @ParamDef(name = "startBirthDate", type = Timestamp.class),
                        @ParamDef(name = "endBirthDate", type = Timestamp.class)
                })
})
@Filters({
        @Filter(name = "nameFilter", condition = "LOWER(name) LIKE CONCAT(LOWER(:searchName), '%')"),
        @Filter(name = "emailFilter", condition = "LOWER(email) LIKE CONCAT(LOWER(:searchEmail), '%')"),
        @Filter(name = "birthDateFilter", condition = ":startBirthDate <= birth_date AND birth_date <= :endBirthDate")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "birth_date", nullable = false)
    private Timestamp birthDate;
}