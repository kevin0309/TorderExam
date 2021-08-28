package io.torder.exam.service.menu.dao;

import io.torder.exam.model.menu.MenuType;
import io.torder.exam.repository.menu.MenuTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuTypeDaoService {

    private final MenuTypeRepository menuTypeRepository;

    public MenuType findAll() {
        return menuTypeRepository.findRootNode().get();
    }
}
