package io.torder.exam.service.menu;

import io.torder.exam.controller.menu.dto.MenuResponse;
import lombok.Getter;

import java.util.ArrayList;

/**
 * MenuService::getAllMenuAsTree 에서 사용될 트리노드를 정의하는 클래스
 */
@Getter
public class MenuTypeTreeNode {

    private final String desc;
    private final ArrayList<MenuTypeTreeNode> children;
    private final ArrayList<MenuResponse> menus;

    public MenuTypeTreeNode(String desc) {
        this.desc = desc;
        this.children = new ArrayList<>();
        this.menus = new ArrayList<>();
    }
    
}
