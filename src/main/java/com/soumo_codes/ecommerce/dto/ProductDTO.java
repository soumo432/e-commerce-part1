package com.soumo_codes.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private int categoryId; //there will be only category id not the category
    private double price;
    private double weight;
    private String description;
    private String imageName;
}
