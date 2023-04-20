package com.myproject.core.user.domain;

import javax.validation.constraints.NotNull;

import com.myproject.core.common.domain.BaseEntity;

public abstract class UserEntity extends BaseEntity{
    
    @NotNull
    private String email;

    @NotNull
    private String userName;

    @NotNull
    private String phoneNumber;

    // private String password;
}
