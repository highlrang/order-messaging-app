package com.myproject.core.user.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.myproject.core.common.domain.BaseEntity;
import com.myproject.core.common.enums.YesNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RIDER")
public class RiderEntity extends UserEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    private YesNo activityYn;

    @NotNull
    private String activityArea;

    @NotNull
    @Enumerated(EnumType.STRING)
    private YesNo deliveringYn;

    @NotNull
    private String nowArea;

    private int penalty;
    

    public void setDeliveringYn(YesNo deliveringYn){
        this.deliveringYn = deliveringYn;
    }
}
