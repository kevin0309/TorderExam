package io.torder.exam.controller.menu;

import io.torder.exam.controller.common.ApiResponse;
import io.torder.exam.controller.menu.dto.OrderMenuResponse;
import io.torder.exam.controller.menu.dto.OrderRequest;
import io.torder.exam.controller.menu.dto.OrderListResponse;
import io.torder.exam.controller.menu.dto.PaymentResponse;
import io.torder.exam.model.transaction.OrderStatus;
import io.torder.exam.controller.user.dto.JwtAuthentication;
import io.torder.exam.service.menu.MenuService;
import io.torder.exam.service.menu.MenuTypeTreeNode;
import io.torder.exam.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 메뉴에 관련된 요청을 핸들링하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/menu")
public class MenuRestController {

    private final MenuService menuService;
    private final TransactionService transactionService;

    /**
     * 모든 메뉴를 메뉴종류에 맞게 트리구조로 조회하는 요청
     */
    @Async
    @GetMapping("tree")
    public CompletableFuture<ApiResponse<MenuTypeTreeNode>> getMenus() {
        return CompletableFuture.completedFuture(ApiResponse.OK(menuService.getAllMenuAsTree()));
    }

    /**
     * 새로운 주문을 처리하는 요청
     */
    @Async
    @PutMapping("order")
    public CompletableFuture<ApiResponse<Boolean>> insertNewOrder(@AuthenticationPrincipal JwtAuthentication auth,
                                                               @RequestBody OrderRequest orderRequest) {
        return CompletableFuture.completedFuture(
                ApiResponse.OK(transactionService.insertOrder(auth.getUserId(), orderRequest)));
    }

    /**
     * 주문접수 상태인 주문목록을 조회하는 요청
     */
    @Async
    @GetMapping("order")
    public CompletableFuture<ApiResponse<OrderListResponse>> getCurOrders(@AuthenticationPrincipal JwtAuthentication auth) {
        return CompletableFuture.completedFuture(ApiResponse.OK(
                transactionService.getOrders(auth.getUserId(), OrderStatus.ACCEPT)));
    }

    /**
     * 계산서를 기반으로 결제요청을 생성하는 요청
     */
    @Async
    @PutMapping("payment")
    public CompletableFuture<ApiResponse<PaymentResponse>> insertNewPayment(@AuthenticationPrincipal JwtAuthentication auth) {
        return CompletableFuture.completedFuture(ApiResponse.OK(transactionService.insertPayment(auth.getUserId())));
    }

    /**
     * 주문목록을 기반으로 계산서를 조회하는 요청
     */
    @Async
    @GetMapping("payment")
    public CompletableFuture<ApiResponse<List<OrderMenuResponse>>> getTotalPayment(
            @AuthenticationPrincipal JwtAuthentication auth) {
        return CompletableFuture.completedFuture(ApiResponse.OK(transactionService.getTotalPaymentMenus(auth.getUserId())));
    }

}
