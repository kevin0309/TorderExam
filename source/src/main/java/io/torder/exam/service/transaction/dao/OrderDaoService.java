package io.torder.exam.service.transaction.dao;

import io.torder.exam.model.transaction.Order;
import io.torder.exam.model.transaction.OrderStatus;
import io.torder.exam.model.user.User;
import io.torder.exam.repository.transaction.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrderRepository 에 직접적으로 접근하는 DAO 서비스클래스
 */
@RequiredArgsConstructor
@Service
public class OrderDaoService {

    private final OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findOrders(User user, OrderStatus status) {
        return orderRepository.findByUserAndStatus(user, status);
    }

}
