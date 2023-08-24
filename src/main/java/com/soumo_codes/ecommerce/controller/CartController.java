package com.soumo_codes.ecommerce.controller;

import com.soumo_codes.ecommerce.global.GlobalData;
import com.soumo_codes.ecommerce.model.Product;
import com.soumo_codes.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id){
        //now we need to add it into global data and return it
        GlobalData.cart.add(productService.getProductById((long) id).get());
        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public String cartGet(Model model){ ///we are using as we return te cart from here
        model.addAttribute("cartCount", GlobalData.cart.size()); //returning total cart count
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());//returning cart price
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemoved(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());//returning cart price
        return "checkout";
    }




}
