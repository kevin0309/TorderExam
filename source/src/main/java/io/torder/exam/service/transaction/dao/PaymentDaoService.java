package io.torder.exam.service.transaction.dao;

import io.torder.exam.model.transaction.Payment;
import io.torder.exam.repository.transaction.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PaymentRepository 에 직접적으로 접근하는 DAO 서비스클래스
 */
@RequiredArgsConstructor
@Service
public class PaymentDaoService {

    private final PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

}
