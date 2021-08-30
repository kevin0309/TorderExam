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

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final UserDaoService userDaoService;
    private final MenuDaoService menuDaoService;
    private final OrderDaoService orderDaoService;
    private final OrderMenuDaoService orderMenuDaoService;
    private final PaymentDaoService paymentDaoService;
    private final PaymentOrderDaoService paymentOrderDaoService;

    @Transactional
    public boolean insertOrder(String userId, OrderRequest orderRequest) {
        User user = userDaoService.findUser(userId);

        if (orderRequest.getMenus().size() == 0)
            throw new IllegalArgumentException("empty menu list!");

        Order newOrder = Order.builder()
                .user(user)
                .status(OrderStatus.ACCEPT)
                .build();
        orderDaoService.save(newOrder);

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

    public OrderListResponse getOrders(String userId, OrderStatus status) {
        User user = userDaoService.findUser(userId);

        OrderListResponse orderListResponse = new OrderListResponse();
        List<Order> orders = orderDaoService.findOrders(user, status);
        Collections.reverse(orders);
        for (Order order : orders) {
            OrderResponse orderResponse = new OrderResponse(
                    order.getRegdate().format(DateTimeFormatter.ofPattern("a hh:mm:ss")),
                    order.getStatus().getDesc());
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

    public PaymentResponse insertPayment(String userId) {
        User user = userDaoService.findUser(userId);
        List<Order> orders = orderDaoService.findOrders(user, OrderStatus.ACCEPT);

        if (orders.size() == 0)
            throw new IllegalArgumentException("order not found");

        Payment payment = Payment.builder()
                .user(user)
                .status(PaymentStatus.ACCEPT)
                .build();
        Payment newPayment = paymentDaoService.save(payment);

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
        List<OrderMenuResponse> totalPayments = getTotalPaymentMenus(userId);
        int totalPrice = 0;
        for (OrderMenuResponse orderMenuResponse : totalPayments)
            totalPrice += orderMenuResponse.getMenu().getPrice() * orderMenuResponse.getQtt();
        PaymentResponse paymentResponse = new PaymentResponse(newPayment.getSeq(),
                totalPrice, newPayment.getStatus().getDesc(),
                newPayment.getRegdate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm:ss")));

        return paymentResponse;
    }

    public List<OrderMenuResponse> getTotalPaymentMenus(String userId) {
        User user = userDaoService.findUser(userId);
        List<Order> orders = orderDaoService.findOrders(user, OrderStatus.ACCEPT);
        List<OrderMenuResponse> orderMenus = new ArrayList<>();

        if (orders.size() == 0)
            return orderMenus;

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
}
