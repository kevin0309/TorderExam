package io.torder.exam.service.transaction.dao;

import io.torder.exam.model.transaction.PaymentOrder;
import io.torder.exam.repository.transaction.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentOrderDaoService {

    private final PaymentOrderRepository paymentOrderRepository;

    public PaymentOrder save(PaymentOrder paymentOrder) {
        return paymentOrderRepository.save(paymentOrder);
    }
}
