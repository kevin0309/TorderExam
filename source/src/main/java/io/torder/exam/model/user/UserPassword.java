package io.torder.exam.model.user;

import io.torder.exam.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * user_password 테이블에 대응하는 도메인 모델을 정의하는 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_password")
public class UserPassword extends BaseEntity<UserPassword> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(nullable = false)
    private String password;

    @Builder
    public UserPassword(User user, String password) {
        this.user = user;
        this.password = password;
    }

}
