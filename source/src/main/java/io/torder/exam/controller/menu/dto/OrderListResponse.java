package io.torder.exam.controller.menu.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderListResponse {

    private List<OrderResponse> orders;

    public OrderListResponse() {
        this.orders = new ArrayList<>();
    }
}
