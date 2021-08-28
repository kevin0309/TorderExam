package io.torder.exam.model.transaction;

import io.torder.exam.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "payment_order")
public class PaymentOrder extends BaseEntity<PaymentOrder> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_seq")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_seq")
    private Order order;

    @Builder
    public PaymentOrder(Payment payment, Order order) {
        this.payment = payment;
        this.order = order;
    }
}
