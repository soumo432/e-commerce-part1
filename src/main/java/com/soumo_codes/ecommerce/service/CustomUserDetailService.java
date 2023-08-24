package com.soumo_codes.ecommerce.service;

import com.soumo_codes.ecommerce.model.CustomUserDetail;
import com.soumo_codes.ecommerce.model.User;
import com.soumo_codes.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    //fetch the user from userrepository and return it
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        //from this optional we will get the object and return it
        user.orElseThrow(() -> new UsernameNotFoundException("User not found"));// if user not found
        return user.map(CustomUserDetail::new).get();
    }
}
