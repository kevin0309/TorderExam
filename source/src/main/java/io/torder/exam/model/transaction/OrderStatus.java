package io.torder.exam.model.transaction;

import lombok.Getter;

@Getter
public enum OrderStatus {

    ACCEPT("주문접수"),
    COOKING("조리중"),
    DELIVERED("완료");

    private final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }
}
