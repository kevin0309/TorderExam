package io.torder.exam.model.menu;

import lombok.Getter;

/**
 * Menu의 상태를 나타낼 Enum
 */
@Getter
public enum MenuStatus {

    FOR_SALE("판매중"),
    PRERARING("준비중"),
    SOLD_OUT("매진");

    private final String desc;

    MenuStatus(String desc) {
        this.desc = desc;
    }

}
