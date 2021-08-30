package io.torder.exam.repository.transaction;

import io.torder.exam.model.transaction.Order;
import io.torder.exam.model.transaction.OrderStatus;
import io.torder.exam.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 도메인 객체 Order 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o join fetch o.user where o.user = ?1 and o.status = ?2")
    List<Order> findByUserAndStatus(User user, OrderStatus status);

}
