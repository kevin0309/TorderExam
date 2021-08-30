package io.torder.exam.model.transaction;

import lombok.Getter;

/**
 * Payment의 상태를 나타낼 Enum
 */
@Getter
public enum PaymentStatus {

    ACCEPT("결제대기중"),
    PARTIALLY_PAID("일부결제"),
    PAID("결제완료");

    private final String desc;

    PaymentStatus(String desc) {
        this.desc = desc;
    }

}

