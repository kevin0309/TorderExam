package io.torder.exam.repository.menu;

import io.torder.exam.model.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("select m from Menu m join fetch m.type")
    List<Menu> findAll();

}
