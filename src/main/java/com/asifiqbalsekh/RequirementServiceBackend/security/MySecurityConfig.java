package com.asifiqbalsekh.RequirementServiceBackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


//Using Default password simulation and DB provided by Spring-security
@Configuration
public class MySecurityConfig {

    //for password encoding mechanism
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //for user details source
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }


    // For security rule, ie. selecting the role
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((configurer)->{
                //Configure role for endpoint

                configurer.requestMatchers(HttpMethod.DELETE,
                        "/api/external-recruitment-service/profile/**",
                        "api/internal-recruitment-service/profile/**"
                        )
                        .hasRole("MANAGER");

                configurer.requestMatchers(
                        "/api/external-recruitment-service/**",
                        "/api/internal-recruitment-service/**"
                        )
                        .hasRole("EMPLOYEE");

                configurer.requestMatchers(
                        "/api/user",
                        "/api/user/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                        )
                        .permitAll();

                configurer.anyRequest().authenticated();

        });

        //Selecting For http basics authorization
        http.httpBasic(Customizer.withDefaults());

        //Disabling CSRF-> not required for rest api
        http.csrf((csrf)->{
            csrf.disable();
        });
        return http.build();

    }

}

/*
For Using Spring provided UserDetails and adding deleting user Spring mention table should be
present in the database. Below query you can run to add those tables

USE `requirement_service`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--
--
-- Default username: admin & passwords here are: fun123
--

INSERT INTO `users` 
VALUES 

('admin','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--


INSERT INTO `authorities` 
VALUES 
('admin','ROLE_EMPLOYEE'),
('admin','ROLE_MANAGER'),
('admin','ROLE_ADMIN');





 */