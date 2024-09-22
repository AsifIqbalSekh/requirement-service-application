package com.asifiqbalsekh.RequirementServiceBackend.security;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    //Register a new User and saving into Spring-security default DB
    @Operation(summary = "Register a new User and save it into Spring-security default DB, Required ADMIN Access",
    description = "It takes user details as request Body")
    @PostMapping
    public String registerUser(@RequestBody UserRegistrationDTO given_user){
        UserDetails user= User.withUsername(given_user.getUsername())
                .password(passwordEncoder.encode(given_user.getPassword()))
                .roles(given_user.getRoles())
                .build();
        userDetailsManager.createUser(user);
        return "user registered";
    }

    @Operation(summary = "Delete given User, Required ADMIN Access",
            description = "It takes username as path variable Body")
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username){
        UserDetails given_user=userDetailsManager.loadUserByUsername(username);
        if(given_user==null){
            throw new UsernameNotFoundException("User not found");
        }
        userDetailsManager.deleteUser(username);
        return "user deleted";
    }
}
