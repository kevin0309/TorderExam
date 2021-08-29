package io.torder.exam.model.transaction;

import io.torder.exam.model.BaseEntity;
import io.torder.exam.model.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order")
public class Order extends BaseEntity<Order> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Builder
    public Order(User user, OrderStatus status) {
        this.user = user;
        this.status = status;
    }
}
