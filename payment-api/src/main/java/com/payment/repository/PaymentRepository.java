package com.payment.repository;

import com.payment.model.Card;
import com.payment.model.Payment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByCardAndStatus(Card card, String status);

    List<Optional<Payment>> findByCardCardNumber(String cardNumber);

    List<Optional<Payment>> findByCustomerCustomerId(String customerId);

    List<Optional<Payment>> findByCardCardNumberAndTimestampBetween(
            String cardNumber, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    List<Optional<Payment>> findByCustomerCustomerIdAndTimestampBetween(
            String customerId, LocalDateTime fromDateTime, LocalDateTime toDateTime);
}
