package dev.jefersonwvs.payment.messaging.event;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreatedEvent(
    String eventId, Long orderId, BigDecimal totalAmount, Instant occurredAt) {}
