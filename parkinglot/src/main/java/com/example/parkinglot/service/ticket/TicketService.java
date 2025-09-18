package com.example.parkinglot.service.ticket;


import com.example.parkinglot.model.ExitInfo;
import com.example.parkinglot.model.Ticket;
import com.example.parkinglot.model.enums.VehicleType;


public interface TicketService {
Ticket createEntryTicket(String plateNo, VehicleType type, Long entryGateId);


Ticket getTicketById(Long ticketId);


/**
* Prepare exit: calculate amount and set ticket to PENDING_PAYMENT
*/
ExitInfo prepareExit(Long ticketId);
}