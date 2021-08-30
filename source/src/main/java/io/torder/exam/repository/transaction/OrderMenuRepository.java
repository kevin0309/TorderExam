package io.torder.exam.repository.transaction;

import io.torder.exam.model.transaction.Order;
import io.torder.exam.model.transaction.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {

    List<OrderMenu> findByOrder(Order order);

}
