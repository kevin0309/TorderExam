package io.torder.exam.service.menu.dao;

import io.torder.exam.repository.menu.MenuOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuOptionDaoService {

    private final MenuOptionRepository menuOptionRepository;

}
