package com.codedmdwsk.sneakerverse.entity;

public enum PaymentStatus {
    INITIATED,   // created locally, provider call might not be done yet
    AUTHORIZED,  // provider authorized (optional state)
    CAPTURED,    // money captured -> PAID
    FAILED,
    CANCELLED,
    REFUNDED
}
