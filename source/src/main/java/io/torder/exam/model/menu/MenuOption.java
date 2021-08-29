package io.torder.exam.model.menu;

import io.torder.exam.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "menu_option")
public class MenuOption extends BaseEntity<MenuOption> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_seq")
    private Menu menu;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private MenuStatus status;

    @Builder
    public MenuOption(Menu menu, String name, Integer price, MenuStatus status) {
        this.menu = menu;
        this.name = name;
        this.price = price;
        this.status = status;
    }
}
