package com.cg.estore.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.estore.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
