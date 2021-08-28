package io.torder.exam.controller.menu.dto;

import lombok.Getter;

@Getter
public class MenuResponse {
    private final int seq;
    private final String name;
    private final int typeSeq;
    private final int price;
    private final String imageUrl;

    public MenuResponse(int seq, String name, int typeSeq, int price, String imageUrl) {
        this.seq = seq;
        this.name = name;
        this.typeSeq = typeSeq;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
