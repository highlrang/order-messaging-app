package com.myproject.core.delivery.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.myproject.core.common.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DELIVERY")
public class DeliveryEntity extends BaseEntity{
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryId;

    private long orderId;

    private long riderId;

    @NotNull
    private String address;

    @NotNull
    private String detailAddress;

    private String deliveryMessage;

    private int deliveryStatus;

    public void setRider(long riderId){
        this.riderId = riderId;
    }

}
