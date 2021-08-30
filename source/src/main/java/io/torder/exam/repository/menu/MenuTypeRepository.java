package io.torder.exam.repository.menu;

import io.torder.exam.model.menu.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 도메인 객체 MenuType 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface MenuTypeRepository extends JpaRepository<MenuType, Integer> {

    @Query("select mt from MenuType mt where mt.descKor = 'root'")
    Optional<MenuType> findRootNode();

}
