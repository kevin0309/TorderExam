package io.torder.exam.service.transaction.dao;

import io.torder.exam.model.transaction.Payment;
import io.torder.exam.repository.transaction.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentDaoService {

    private final PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

}
