package com.example.fourth.controller;

import com.example.fourth.domain.Member;
import com.example.fourth.model.order.OrderSaveRequest;
import com.example.fourth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/members/{memberId}/orders")
    public ResponseEntity saveOrder(@PathVariable Long memberId, @RequestBody @Valid OrderSaveRequest orderSaveRequest) {
        Long savedOrderId = orderService.createOrder(memberId, orderSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderId);
    }
}
