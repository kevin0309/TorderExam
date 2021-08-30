package io.torder.exam.repository.user;

import io.torder.exam.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 도메인 객체 User 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(String userId);

}
