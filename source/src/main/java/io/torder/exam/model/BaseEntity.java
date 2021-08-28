package io.torder.exam.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA entity 모델 클래스에서 공통적으로 사용할 컬럼에 대한 정의 클래스
 * 모든 entity 모델 클래스는 해당 클래스를 상속받아 사용하도록 한다.
 *
 * 공통 컬럼으로는
 * MySQL의 권장사항인 auto_increment 되는 PK인 seq 컬럼
 * 레코드가 생성된 날짜를 나타내는 regdate 컬럼
 * 레코드가 마지막으로 수정된 날짜를 나타내는 moddate 컴럼이 있다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @CreatedDate
    private LocalDateTime regdate;

    @LastModifiedDate
    private LocalDateTime moddate;
}
