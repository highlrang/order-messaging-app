package com.myproject.core.common.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate 
    private LocalDateTime insertDate;
    @LastModifiedDate
    private LocalDateTime updateDate;
    private String insertOperator;
    private String updateOperator;
    private String deleteYn;
}
