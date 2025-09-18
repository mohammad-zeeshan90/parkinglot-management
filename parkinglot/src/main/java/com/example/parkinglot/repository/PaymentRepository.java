package com.example.parkinglot.repository;


import com.example.parkinglot.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
Optional<Payment> findByTicket_Id(Long ticketId);
}