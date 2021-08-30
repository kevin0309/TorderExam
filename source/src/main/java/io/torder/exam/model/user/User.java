package io.torder.exam.model.user;

import io.torder.exam.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * user 테이블에 대응하는 도메인 모델을 정의하는 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity<User> {

    @Column(nullable = false, name = "id")
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role_code")
    private Role role;

    @Builder
    public User(String userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

}
