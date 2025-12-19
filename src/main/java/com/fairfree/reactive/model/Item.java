package com.fairfree.reactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("items")
public class Item {
    @Id
    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    @Column("owner_id")
    private Long ownerId;  // foreign key to User, user will have owner_id as FK
}
