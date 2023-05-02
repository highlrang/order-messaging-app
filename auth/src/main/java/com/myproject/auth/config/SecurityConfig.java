package com.myproject.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myproject.auth.service.AuthService;

import static org.springframework.security.config.Customizer.withDefaults;

// TODO filter로 해야하나??

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired private AuthService authService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http  
                .authorizeRequests(
                        requests -> requests
                                .antMatchers("/api/v1/products", "/api/v1/products/**", "/api/v1/auth")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(authService)
                .csrf().disable();
                
            
            
    }


}
