package io.torder.exam.repository.menu;

import io.torder.exam.model.menu.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuTypeRepository extends JpaRepository<MenuType, Integer> {

}
