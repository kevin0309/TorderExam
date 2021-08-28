package io.torder.exam.repository.menu;

import io.torder.exam.model.menu.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuTypeRepository extends JpaRepository<MenuType, Integer> {

    @Query("select mt from MenuType mt where mt.descKor = 'root'")
    Optional<MenuType> findRootNode();

}
