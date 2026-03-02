package com.codedmdwsk.sneakerverse.entity;

public enum PaymentEventType {
    PROVIDER_REQUESTED,
    WEBHOOK_RECEIVED,
    STATUS_CHANGED,
    CAPTURED,
    FAILED,
    REFUNDED
}
