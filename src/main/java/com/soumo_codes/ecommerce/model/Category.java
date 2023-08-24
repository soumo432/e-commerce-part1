package com.soumo_codes.ecommerce.model;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity  //It will create table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")// in RDBMS the format is with "_" like category_id
    private int id;

    private String name;
}
