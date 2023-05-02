package com.myproject.core.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCollectionDto {
    private OrderDto orderDto;
    private List<OrderProductDto> orderProductDtos;
}
