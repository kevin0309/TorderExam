package io.torder.exam.service.menu;

import io.torder.exam.controller.menu.dto.MenuResponse;
import io.torder.exam.model.menu.Menu;
import io.torder.exam.model.menu.MenuType;
import io.torder.exam.service.menu.dao.MenuDaoService;
import io.torder.exam.service.menu.dao.MenuTypeDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuDaoService menuDaoService;
    private final MenuTypeDaoService menuTypeDaoService;

    /**
     * 모든 메뉴를 트리형식으로 조회하는 메서드
     * 메뉴타입은 최대 2단계의 트리로 고정되어있지만 확장성을 위해 순회하며 트리를 생성하도록 구현
     */
    public MenuTypeTreeNode getAllMenuAsTree() {
        MenuType menuTypesTree = menuTypeDaoService.findAll();
        List<Menu> menus = menuDaoService.findAll();

        //순회하며 MenuTypeTreeElement로 변환
        MenuTypeTreeNode rootNode = new MenuTypeTreeNode(menuTypesTree.getDescKor());
        for (MenuType mt : menuTypesTree.getChildren())
            traverse(rootNode, mt, menus);

        return rootNode;
    }

    /**
     * 트리순회를 담당하는 재귀함수
     * 생성되지 않은 tree를 새로 만들며 순회하기 위해
     * 부모 노드를 파라미터로 받기 때문에 2단계에 걸쳐서 사용해야함
     */
    private void traverse(MenuTypeTreeNode parent, MenuType menuType, List<Menu> menus) {
        MenuTypeTreeNode newNode = new MenuTypeTreeNode(menuType.getDescKor());
        for (Menu menu : menus)
            if (menu.getType().getSeq() == menuType.getSeq())
                newNode.getMenus().add(new MenuResponse(menu.getSeq(), menu.getName(),
                        menu.getType().getSeq(), menu.getPrice(), menu.getImageUrl()));
        parent.getChildren().add(newNode);

        for (MenuType mt : menuType.getChildren())
            traverse(newNode, mt, menus);
    }
}
