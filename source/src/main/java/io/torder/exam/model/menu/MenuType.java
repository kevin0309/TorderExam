package io.torder.exam.model.menu;

import io.torder.exam.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * menu_type 테이블에 대응하는 도메인 모델을 정의하는 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "menu_type")
public class MenuType extends BaseEntity<MenuType> {

    @Column(nullable = false, name = "desc_kor")
    private String descKor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_seq")
    private MenuType parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<MenuType> children;

    @Builder
    public MenuType(String descKor, MenuType parent, List<MenuType> children) {
        this.descKor = descKor;
        this.parent = parent;
        this.children = children;
    }

}
