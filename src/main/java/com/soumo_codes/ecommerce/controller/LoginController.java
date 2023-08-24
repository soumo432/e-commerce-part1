package com.soumo_codes.ecommerce.controller;

import com.soumo_codes.ecommerce.global.GlobalData;
import com.soumo_codes.ecommerce.model.Role;
import com.soumo_codes.ecommerce.model.User;
import com.soumo_codes.ecommerce.repository.RoleRepository;
import com.soumo_codes.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear(); //after logout the cart count will be 0
        return "login";
    }
    @GetMapping("/register")
    public String registerGet(){
        return "register";
    }

    @PostMapping("/register")
    //saving the user and HttpServletRequest helps to log in directly after registering
    public String registerPost(@ModelAttribute("User") User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword(); // get the password
        user.setPassword(bCryptPasswordEncoder.encode(password));// encode it
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        request.login(user.getEmail(),password);//it will log in directly
        return "redirect:/";
    }
}
