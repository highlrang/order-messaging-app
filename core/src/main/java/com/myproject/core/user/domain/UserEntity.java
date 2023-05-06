package com.myproject.core.user.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.myproject.core.common.domain.BaseEntity;
import com.myproject.core.user.enums.UserRoleType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter @Setter
public abstract class UserEntity extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NotNull
    private String email;

    @NotNull
    private String userName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleType userRole;
}
