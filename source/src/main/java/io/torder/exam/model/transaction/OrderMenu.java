package io.torder.exam.model.transaction;

import io.torder.exam.model.BaseEntity;
import io.torder.exam.model.menu.Menu;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ordering_menu 테이블에 대응하는 도메인 모델을 정의하는 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ordering_menu")
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
