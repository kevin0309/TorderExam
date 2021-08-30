package io.torder.exam.repository.menu;

import io.torder.exam.model.menu.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 도메인 객체 MenuOption 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Integer> {

}
