package io.torder.exam.model.transaction;

import io.torder.exam.model.BaseEntity;
import io.torder.exam.model.menu.Menu;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order")
public class OrderMenu extends BaseEntity<OrderMenu> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_seq")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_seq")
    private Menu menu;

    @Column(nullable = false)
    private Integer qtt;

    @Builder
    public OrderMenu(Order order, Menu menu, Integer qtt) {
        this.order = order;
        this.menu = menu;
        this.qtt = qtt;
    }
}
