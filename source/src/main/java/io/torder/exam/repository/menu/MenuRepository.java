package io.torder.exam.repository.menu;

import io.torder.exam.model.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 도메인 객체 Menu 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("select m from Menu m join fetch m.type")
    List<Menu> findAll();

    Optional<Menu> findBySeq(Integer seq);

}
