package io.torder.exam.controller.user.dto;

public class ModResponse {
    private final String id;
    private boolean isSuccess;

    public ModResponse(String id, boolean isSuccess) {
        this.id = id;
        this.isSuccess = isSuccess;
    }
}
