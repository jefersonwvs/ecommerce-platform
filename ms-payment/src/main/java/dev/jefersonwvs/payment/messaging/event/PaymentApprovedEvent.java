package dev.jefersonwvs.payment.messaging.event;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentApprovedEvent(
    String eventId, Long orderId, Long paymentId, BigDecimal amount, Instant occurredAt) {}
