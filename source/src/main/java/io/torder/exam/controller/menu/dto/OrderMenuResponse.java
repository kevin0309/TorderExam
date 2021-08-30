package io.torder.exam.controller.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuResponse {

    private MenuResponse menu;
    private int qtt;

    public OrderMenuResponse(MenuResponse menu, int qtt) {
        this.menu = menu;
        this.qtt = qtt;
    }
}
