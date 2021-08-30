package io.torder.exam.service.menu.dao;

import io.torder.exam.repository.menu.MenuOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * MenuOptionRepository 에 직접적으로 접근하는 DAO 서비스클래스
 */
@RequiredArgsConstructor
@Service
public class MenuOptionDaoService {

    private final MenuOptionRepository menuOptionRepository;

}
