package io.torder.exam.controller.menu.dto;

import lombok.Getter;

@Getter
public class PaymentResponse {

    private int seq;
    private int totalPrice;
    private String status;
    private String timestamp;

    public PaymentResponse(int seq, int totalPrice, String status, String timestamp) {
        this.seq = seq;
        this.totalPrice = totalPrice;
        this.status = status;
        this.timestamp = timestamp;
    }
}
