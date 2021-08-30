package io.torder.exam.service.transaction.dao;

import io.torder.exam.model.transaction.Order;
import io.torder.exam.model.transaction.OrderMenu;
import io.torder.exam.model.user.User;
import io.torder.exam.repository.transaction.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderMenuDaoService {

    private final OrderMenuRepository orderMenuRepository;

    public OrderMenu save(OrderMenu orderMenu) {
        return orderMenuRepository.save(orderMenu);
    }

    public List<OrderMenu> findOrders(Order order) {
        return orderMenuRepository.findByOrder(order);
    }
}
