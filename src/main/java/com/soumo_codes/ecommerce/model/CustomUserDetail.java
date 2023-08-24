package com.soumo_codes.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail extends User implements UserDetails {

    //now we need to return the username and it will come from constructor which we will create below
    public CustomUserDetail(User user){
        super(user); //user will pass it to the parent
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //we need to create a list as a user has many roles which also consists a list
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //now we will get the roles and will add in granted authority
        super.getRoles().forEach(role -> {
                    authorityList.add(new SimpleGrantedAuthority(role.getName()));// we need to convert the role into a class of Granted Authority
                });
        return authorityList;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
