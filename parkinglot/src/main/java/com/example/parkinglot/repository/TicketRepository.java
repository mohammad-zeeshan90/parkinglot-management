package com.example.parkinglot.repository;


import com.example.parkinglot.model.Ticket;
import com.example.parkinglot.model.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {


Optional<Ticket> findByTicketNo(String ticketNo);


// find active ticket by vehicle plate number
Optional<Ticket> findByVehicle_PlateNoAndStatus(String plateNo, TicketStatus status);
}