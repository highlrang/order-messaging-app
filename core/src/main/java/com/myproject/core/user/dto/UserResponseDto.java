package com.myproject.core.user.dto;

import java.util.Arrays;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.UserEntity;
import com.myproject.core.user.enums.UserRoleType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserResponseDto {

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
    
}
