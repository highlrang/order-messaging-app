package com.myproject.storeapi.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.delivery.repository.DeliveryHistoryRepository;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.user.repository.RiderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryMatchingService {
    
    private final DeliveryRepository deliveryRepository;
    private final RiderRepository riderRepository;

    @Transactional
    public void match(long orderId){

    }
}
