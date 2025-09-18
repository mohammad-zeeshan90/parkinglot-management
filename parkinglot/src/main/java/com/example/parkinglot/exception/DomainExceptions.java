package com.example.parkinglot.exception;


public class DomainExceptions {
public static class LotFullException extends RuntimeException {
public LotFullException(String message) { super(message); }
}


public static class DuplicateVehicleEntryException extends RuntimeException {
public DuplicateVehicleEntryException(String message) { super(message); }
}


public static class TicketNotFoundException extends RuntimeException {
public TicketNotFoundException(String message) { super(message); }
}


public static class PaymentFailedException extends RuntimeException {
public PaymentFailedException(String message) { super(message); }
}


public static class InvalidOperationException extends RuntimeException {
public InvalidOperationException(String message) { super(message); }
}
}