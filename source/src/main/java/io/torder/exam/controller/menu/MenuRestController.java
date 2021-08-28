package io.torder.exam.controller.menu;

import io.torder.exam.controller.common.ApiResponse;
import io.torder.exam.controller.menu.dto.MenuResponse;
import io.torder.exam.service.menu.MenuService;
import io.torder.exam.service.menu.MenuTypeTreeNode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/menu")
public class MenuRestController {

    private final MenuService menuService;

    @Async
    @GetMapping("tree")
    public CompletableFuture<ApiResponse<MenuTypeTreeNode>> getMenus() {
        return CompletableFuture.completedFuture(ApiResponse.OK(menuService.getAllMenuAsTree()));
    }

}
