package io.torder.exam.controller.menu;

import io.torder.exam.controller.common.ApiResponse;
import io.torder.exam.controller.menu.dto.OrderMenuResponse;
import io.torder.exam.controller.menu.dto.OrderRequest;
import io.torder.exam.controller.menu.dto.OrderListResponse;
import io.torder.exam.controller.menu.dto.PaymentResponse;
import io.torder.exam.model.transaction.OrderStatus;
import io.torder.exam.security.dto.JwtAuthentication;
import io.torder.exam.service.menu.MenuService;
import io.torder.exam.service.menu.MenuTypeTreeNode;
import io.torder.exam.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/menu")
public class MenuRestController {

    private final MenuService menuService;
    private final TransactionService transactionService;

    @Async
    @GetMapping("tree")
    public CompletableFuture<ApiResponse<MenuTypeTreeNode>> getMenus() {
        return CompletableFuture.completedFuture(ApiResponse.OK(menuService.getAllMenuAsTree()));
    }

    @Async
    @PutMapping("order")
    public CompletableFuture<ApiResponse<Boolean>> insertNewOrder(@AuthenticationPrincipal JwtAuthentication auth,
                                                               @RequestBody OrderRequest orderRequest) {
        return CompletableFuture.completedFuture(
                ApiResponse.OK(transactionService.insertOrder(auth.getUserId(), orderRequest)));
    }

    @Async
    @GetMapping("order")
    public CompletableFuture<ApiResponse<OrderListResponse>> getCurOrders(@AuthenticationPrincipal JwtAuthentication auth) {
        return CompletableFuture.completedFuture(ApiResponse.OK(
                transactionService.getOrders(auth.getUserId(), OrderStatus.ACCEPT)));
    }

    @Async
    @PutMapping("payment")
    public CompletableFuture<ApiResponse<PaymentResponse>> insertNewPayment(@AuthenticationPrincipal JwtAuthentication auth) {
        return CompletableFuture.completedFuture(ApiResponse.OK(transactionService.insertPayment(auth.getUserId())));
    }

    @Async
    @GetMapping("payment")
    public CompletableFuture<ApiResponse<List<OrderMenuResponse>>> getTotalPayment(
            @AuthenticationPrincipal JwtAuthentication auth) {
        return CompletableFuture.completedFuture(ApiResponse.OK(transactionService.getTotalPaymentMenus(auth.getUserId())));
    }

}
