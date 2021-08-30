package io.torder.exam.service.transaction;

import io.torder.exam.controller.menu.dto.*;
import io.torder.exam.model.menu.Menu;
import io.torder.exam.model.transaction.*;
import io.torder.exam.model.user.User;
import io.torder.exam.service.menu.dao.MenuDaoService;
import io.torder.exam.service.transaction.dao.OrderDaoService;
import io.torder.exam.service.transaction.dao.OrderMenuDaoService;
import io.torder.exam.service.transaction.dao.PaymentDaoService;
import io.torder.exam.service.transaction.dao.PaymentOrderDaoService;
import io.torder.exam.service.user.dao.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order, Payment와 같은 거래에 관련한 비즈니스로직을 구현한 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final UserDaoService userDaoService;
    private final MenuDaoService menuDaoService;
    private final OrderDaoService orderDaoService;
    private final OrderMenuDaoService orderMenuDaoService;
    private final PaymentDaoService paymentDaoService;
    private final PaymentOrderDaoService paymentOrderDaoService;

    /**
     * 새로운 주문을 받아 insert 시키는 메서드
     */
    @Transactional
    public boolean insertOrder(String userId, OrderRequest orderRequest) {
        User user = userDaoService.findUser(userId);

        //메뉴가 비어있을 시 에러발생
        if (orderRequest.getMenus().size() == 0)
            throw new IllegalArgumentException("empty menu list!");

        //새로운 Order 트랜잭션 생성 후 insert
        Order newOrder = Order.builder()
                .user(user)
                .status(OrderStatus.ACCEPT)
                .build();
        orderDaoService.save(newOrder);

        //Order의 내용인 Menu를 각각 생성 후 insert
        for (OrderRequestElement e : orderRequest.getMenus()) {
            OrderMenu newOrderMenu = OrderMenu.builder()
                    .order(newOrder)
                    .menu(menuDaoService.findMenu(e.getMenuSeq()))
                    .qtt(e.getQtt())
                    .build();
            orderMenuDaoService.save(newOrderMenu);
        }

        return true;
    }

    /**
     * 주문목록을 조회하는 메서드
     */
    public OrderListResponse getOrders(String userId, OrderStatus status) {
        User user = userDaoService.findUser(userId);

        OrderListResponse orderListResponse = new OrderListResponse();
        //먼저 해당 사용자의 Order를 조회
        List<Order> orders = orderDaoService.findOrders(user, status);
        Collections.reverse(orders);
        //Order가 여러개 존재할 수 있으므로 각각의 Order에 대하여 처리
        for (Order order : orders) {
            OrderResponse orderResponse = new OrderResponse(
                    order.getRegdate().format(DateTimeFormatter.ofPattern("a hh:mm:ss")),
                    order.getStatus().getDesc());
            //Order에 대한 Menu 목록을 조회
            List<OrderMenu> orderMenus = orderMenuDaoService.findOrders(order);
            for (OrderMenu orderMenu : orderMenus) {
                Menu menu = orderMenu.getMenu();
                OrderMenuResponse orderMenuResponse = new OrderMenuResponse(new MenuResponse(menu), orderMenu.getQtt());
                orderResponse.getOrderMenus().add(orderMenuResponse);
            }
            orderListResponse.getOrders().add(orderResponse);
        }

        return orderListResponse;
    }

    /**
     * 주문목록에 기반한 계산서 조회 메서드
     */
    public List<OrderMenuResponse> getTotalPaymentMenus(String userId) {
        User user = userDaoService.findUser(userId);
        List<Order> orders = orderDaoService.findOrders(user, OrderStatus.ACCEPT);
        List<OrderMenuResponse> orderMenus = new ArrayList<>();

        //주문이 없다면 빈배열 리턴
        if (orders.size() == 0)
            return orderMenus;

        //각각의 order를 순회하며 결제해야할 Menu 정보를 종합 (메뉴, 메뉴갯수)
        for (Order order : orders) {
            List<OrderMenu> tempOrderMenus = orderMenuDaoService.findOrders(order);
            for (OrderMenu orderMenu : tempOrderMenus) {
                boolean flag = true;
                for (OrderMenuResponse tempOrderMenu : orderMenus) {
                    if (orderMenu.getMenu().getSeq() == tempOrderMenu.getMenu().getSeq()) {
                        tempOrderMenu.setQtt(tempOrderMenu.getQtt() + orderMenu.getQtt());
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    orderMenus.add(new OrderMenuResponse(new MenuResponse(orderMenu.getMenu()), orderMenu.getQtt()));
            }
        }

        //return orderMenus.stream().sorted(Comparator.comparingInt((orderMenu) -> orderMenu.getMenu().getSeq()))
        //        .collect(Collectors.toList());
        return orderMenus;
    }

    /**
     * 결제 트랜잭션을 생성하는 메서드
     */
    public PaymentResponse insertPayment(String userId) {
        User user = userDaoService.findUser(userId);
        List<Order> orders = orderDaoService.findOrders(user, OrderStatus.ACCEPT);

        //주문이 없다면 에러 발생
        if (orders.size() == 0)
            throw new IllegalArgumentException("order not found");

        //새로운 Payment 트랜잭션 생성 후 insert
        Payment payment = Payment.builder()
                .user(user)
                .status(PaymentStatus.ACCEPT)
                .build();
        Payment newPayment = paymentDaoService.save(payment);

        //각각의 Order에 대하여 paymentOrder 생성 후 insert
        for (Order order : orders) {
            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .payment(payment)
                    .order(order)
                    .build();
            paymentOrderDaoService.save(paymentOrder);

            //주문내역 상태변경
            order.updateModdate();
            order.updateStatus(OrderStatus.PAID);
            orderDaoService.save(order);
        }
        
        //생성된 Payment 트랜잭션에 대한 요약정보를 묶어서 리턴
        List<OrderMenuResponse> totalPayments = getTotalPaymentMenus(userId);
        int totalPrice = 0;
        for (OrderMenuResponse orderMenuResponse : totalPayments)
            totalPrice += orderMenuResponse.getMenu().getPrice() * orderMenuResponse.getQtt();
        PaymentResponse paymentResponse = new PaymentResponse(newPayment.getSeq(),
                totalPrice, newPayment.getStatus().getDesc(),
                newPayment.getRegdate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm:ss")));

        return paymentResponse;
    }

}
