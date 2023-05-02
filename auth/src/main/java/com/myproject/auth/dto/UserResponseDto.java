package com.myproject.auth.dto;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.UserEntity;
import com.myproject.core.user.enums.UserRoleType;

import lombok.Getter;

@Getter
public class UserResponseDto implements UserDetails {

    private long userId;
    private String email;
    private String userName;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private UserRoleType userRole;

    public UserResponseDto(UserEntity e){
        this.userId = e.getUserId();
        this.email = e.getEmail();
        this.userName = e.getUserName();
        this.phoneNumber = e.getPhoneNumber();
        this.password = e.getPassword();
        this.userRole = e.getUserRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.getUserRole().getCode()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
