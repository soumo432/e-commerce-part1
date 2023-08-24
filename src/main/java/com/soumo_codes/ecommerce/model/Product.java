package com.soumo_codes.ecommerce.model;

import javax.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category; //this category object cannot be handled directly into frontend, so we need to create a dto
    private double price;
    private double weight;
    private String description;
    private String imageName; //in database only consist the name of the image and not the image
}
