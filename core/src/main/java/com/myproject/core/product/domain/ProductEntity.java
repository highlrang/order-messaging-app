package com.myproject.core.product.domain;

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
@Table(name = "PRODUCT")
public class ProductEntity extends BaseEntity{
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    
    private long storeId;

    private long categoryId;

    @NotNull
    private String productName;
    
    private int productPrice;

    
}
