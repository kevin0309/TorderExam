package io.torder.exam.repository.user;

import io.torder.exam.model.user.User;
import io.torder.exam.model.user.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 도메인 객체 UserPassword 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {

    @Query("select up from UserPassword up join fetch up.user where up.user = ?1 order by up.regdate desc")
    List<UserPassword> findAllByUser(User user);

}
