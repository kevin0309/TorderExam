package io.torder.exam.repository.transaction;

import io.torder.exam.model.transaction.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 도메인 객체 Payment 에서 사용될 CRUD 기능을 정의하는 인터페이스
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
