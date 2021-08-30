package io.torder.exam.service.menu.dao;

import io.torder.exam.model.menu.Menu;
import io.torder.exam.repository.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * MenuRepository 에 직접적으로 접근하는 DAO 서비스클래스
 */
@RequiredArgsConstructor
@Service
public class MenuDaoService {

    private final MenuRepository menuRepository;

    /**
     * 메뉴 전체를 조회하는 메서드
     * 해당 프로젝트에서는 메뉴를 음식점별로 따로 구현하지 않음
     */
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findMenu(Integer menuSeq) {
        return menuRepository.findBySeq(menuSeq).get();
    }

}
