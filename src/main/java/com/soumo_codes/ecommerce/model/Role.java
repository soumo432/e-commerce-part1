package com.soumo_codes.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false,unique = true)
    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "roles")//we have mapped with this tanle and one role can have many user
    private List<User> users;
}
