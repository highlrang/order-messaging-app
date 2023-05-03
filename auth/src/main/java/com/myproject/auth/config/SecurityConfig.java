package com.myproject.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myproject.auth.filter.AuthenticationFilter;
import com.myproject.auth.service.AuthService;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired private AuthService authService;

    @Autowired private AuthenticationFilter authenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
	public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/api/v1/auth", "/api/v1/products**");
	}

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http  
                // .authorizeRequests()    
                //     .antMatchers("/api/v1/products", "/api/v1/products**")
                //         .permitAll()
                //     .anyRequest()
                //         .authenticated()
                // .and()
                .userDetailsService(authService)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
                
            
            
    }


}
