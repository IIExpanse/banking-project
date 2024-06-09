package ru.expanse.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "linked_products")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Product> linkedProducts;
    @Column(name = "date_created", nullable = false)
    private Timestamp dateCreated;
}
