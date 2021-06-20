package com.example.fourth.model.order;

import lombok.Data;

@Data
public class OrderSaveRequest {

    private Long productId;
    private Integer count;
}
