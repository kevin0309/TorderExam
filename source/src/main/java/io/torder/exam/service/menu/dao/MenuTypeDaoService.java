package io.torder.exam.service.menu.dao;

import io.torder.exam.model.menu.MenuType;
import io.torder.exam.repository.menu.MenuTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MenuTypeRepository 에 직접적으로 접근하는 DAO 서비스클래스
 */
@RequiredArgsConstructor
@Service
public class MenuTypeDaoService {

    private final MenuTypeRepository menuTypeRepository;

    public MenuType findAll() {
        return menuTypeRepository.findRootNode().get();
    }

}
