package io.torder.exam.controller.user.dto;

import lombok.Getter;

@Getter
public class JoinResponse {

    private final String id;
    private boolean isSuccess;

    public JoinResponse(String id, boolean isSuccess) {
        this.id = id;
        this.isSuccess = isSuccess;
    }

}
