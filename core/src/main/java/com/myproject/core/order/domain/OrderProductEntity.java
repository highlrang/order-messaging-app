package com.myproject.core.order.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderProductId;

    private long orderId;

    private long productId;

    @NotNull
    private String productName;

    private int orderQuantity;

    private int orderPrice;

}
