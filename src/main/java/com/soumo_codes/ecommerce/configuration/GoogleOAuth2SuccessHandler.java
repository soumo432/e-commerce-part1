package com.soumo_codes.ecommerce.configuration;

import com.soumo_codes.ecommerce.model.Role;
import com.soumo_codes.ecommerce.model.User;
import com.soumo_codes.ecommerce.repository.RoleRepository;
import com.soumo_codes.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
//as googleOAuth2SuccessHandler is a class we need the object for it
//AuthenticationSuccessHandler used to handle a successful user authentication.
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    //we need the userRepository and roleRepository
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();//this is used to redirect internally

    //we will take oauth2 authentication and if authentication is successful by google, google will give a token
    //which we will receive("authentication" will give the token) and we will cast the token into OAuth token
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication; //casting
        //we called logged in user as principal
        String email= token.getPrincipal().getAttributes().get("email").toString();

        //if user already exists in database then we will  then we will redirect it or will get the user details and save it.
        if(userRepository.findUserByEmail(email).isPresent()){
//            System.out.println("Google knows this user");
        }
        else {
            //here if we got a new user, will set the details of the user
//            System.out.println("Google added this user to database");
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());//given_name gives first name form google
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());//family_name gives last name form google
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());//in database in position 2, that role will be added in database
            user.setRoles(roles);
            userRepository.save(user);
        }
        //if google tells he knows this user it will redirect to home
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse,"/");
    }
}
