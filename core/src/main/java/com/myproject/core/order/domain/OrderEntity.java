package com.myproject.core.order.domain;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.myproject.core.common.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ORDER_STATUS")
public class OrderEntity extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private long memberId;

    @NotNull
    private String orderName;

    private int orderPrice;

    private int orderStatus;
    
    public void setOrderInfo(String orderName, int orderPrice){
        this.orderName = orderName;
        this.orderPrice = orderPrice;
    }

    
    
}
