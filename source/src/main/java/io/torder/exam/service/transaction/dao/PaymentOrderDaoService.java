package io.torder.exam.service.transaction.dao;

import io.torder.exam.model.transaction.PaymentOrder;
import io.torder.exam.repository.transaction.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PaymentOrderRepository 에 직접적으로 접근하는 DAO 서비스클래스
 */
@RequiredArgsConstructor
@Service
public class PaymentOrderDaoService {

    private final PaymentOrderRepository paymentOrderRepository;

    public PaymentOrder save(PaymentOrder paymentOrder) {
        return paymentOrderRepository.save(paymentOrder);
    }

}
