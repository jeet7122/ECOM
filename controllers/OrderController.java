package com.ecommerce.EcomApplication.controllers;

import com.ecommerce.EcomApplication.dtos.OrderResponse;
import com.ecommerce.EcomApplication.services.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-ID") String userId){
        return orderService.createOrder(userId)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
