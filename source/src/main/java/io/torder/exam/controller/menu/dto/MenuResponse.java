package io.torder.exam.controller.menu.dto;

import io.torder.exam.model.menu.Menu;
import lombok.Getter;

@Getter
public class MenuResponse {

    private final int seq;
    private final String name;
    private final int typeSeq;
    private final int price;
    private final String status;
    private final String imageUrl;

    public MenuResponse(Menu menu) {
        this.seq = menu.getSeq();
        this.name = menu.getName();
        this.typeSeq = menu.getType().getSeq();
        this.price = menu.getPrice();
        this.status = menu.getStatus().getDesc();
        this.imageUrl = menu.getImageUrl();
    }

}
