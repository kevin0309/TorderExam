package io.torder.exam.controller.user.dto;

import lombok.Getter;

@Getter
public class ModResponse {

    private final String id;
    private boolean isSuccess;

    public ModResponse(String id, boolean isSuccess) {
        this.id = id;
        this.isSuccess = isSuccess;
    }

}
