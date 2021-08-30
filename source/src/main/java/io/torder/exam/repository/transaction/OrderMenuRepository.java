package io.torder.exam.repository.transaction;

import io.torder.exam.model.transaction.Order;
import io.torder.exam.model.transaction.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 도메인 객체 OrderMenu 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {

    List<OrderMenu> findByOrder(Order order);

}
