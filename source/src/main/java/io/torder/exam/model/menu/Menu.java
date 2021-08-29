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
@Table(name = "menu")
public class Menu extends BaseEntity<Menu> {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private MenuType type;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status;

    @Column(nullable = true, name = "img_url")
    private String imageUrl;

    @Builder
    public Menu(String name, MenuType type, Integer price, MenuStatus status, String imageUrl) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.status = status;
        this.imageUrl = imageUrl;
    }
}
