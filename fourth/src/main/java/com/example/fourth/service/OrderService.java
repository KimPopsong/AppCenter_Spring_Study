package com.example.fourth.service;

import com.example.fourth.domain.Member;
import com.example.fourth.domain.Order;
import com.example.fourth.domain.Product;
import com.example.fourth.exception.MemberException;
import com.example.fourth.model.order.OrderSaveRequest;
import com.example.fourth.repository.MemberRepository;
import com.example.fourth.repository.OrderRepository;
import com.example.fourth.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long createOrder(Long memberId, OrderSaveRequest orderSaveRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("Not Existing Member"));

        Product findProduct = productRepository.findById(orderSaveRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("TODO"));

        Order order = Order.createOrder(orderSaveRequest.getCount(), findMember, findProduct);
        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }
}
