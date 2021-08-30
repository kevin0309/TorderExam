package io.torder.exam.model.transaction;

import lombok.Getter;

/**
 * Order의 상태를 나타낼 Enum
 */
@Getter
public enum OrderStatus {

    ACCEPT("주문접수"),
    PAID("결제완료"),
    CANCELLED("취소");

    private final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }

}
