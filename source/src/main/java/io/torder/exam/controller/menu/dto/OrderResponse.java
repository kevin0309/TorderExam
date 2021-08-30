package io.torder.exam.controller.menu.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponse {

    private String timestamp;
    private String status;
    private List<OrderMenuResponse> orderMenus;

    public OrderResponse(String timestamp, String status) {
        this.timestamp = timestamp;
        this.status = status;
        this.orderMenus = new ArrayList<>();
    }
}
