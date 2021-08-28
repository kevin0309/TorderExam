package io.torder.exam.model.menu;

import io.torder.exam.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "menu_type")
public class MenuType extends BaseEntity {

    @Column(nullable = false, name = "desc_kor")
    private String descKor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_seq")
    private MenuType parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<MenuType> children;

}
