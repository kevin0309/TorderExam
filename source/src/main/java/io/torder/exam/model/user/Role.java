package io.torder.exam.model.user;

import lombok.Getter;

/**
 * 각 사용자의 권한을 나타낼 Enum
 * 권한은 비회원, 회원, 관리자로 간단히 정하였다.
 */
@Getter
public enum Role {

    GUEST("ROLE_GUEST", "비회원"),
    USER("ROLE_USER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String code;
    private final String desc;

    Role(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
