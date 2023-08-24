package com.soumo_codes.ecommerce.global;

import com.soumo_codes.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

//Global data is used for wherever the cart count(right corner) is present we need to show the data of the cart count
//when user will log in
//if we do not make this the count will not be visible

public class GlobalData {
    public static List<Product> cart; //cart consists product, so list type will be product
    static {
        cart = new ArrayList<Product>();
    }
}
