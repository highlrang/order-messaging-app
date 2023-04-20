package com.myproject.core.order.domain;
import lombok.*;
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

    private long productId;

    @NotNull
    private String productName;

    private long orderPrice;

    private int orderQuantity;

    private int orderStatus;
    
    
    
}
