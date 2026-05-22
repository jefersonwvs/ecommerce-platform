package dev.jefersonwvs.payment.messaging.consumer;

import dev.jefersonwvs.payment.messaging.config.RabbitMQConfig;
import dev.jefersonwvs.payment.messaging.event.OrderCreatedEvent;
import dev.jefersonwvs.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

  private static final Logger logger = LoggerFactory.getLogger(OrderCreatedConsumer.class);

  private final PaymentService paymentService;

  public OrderCreatedConsumer(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @RabbitListener(queues = RabbitMQConfig.PAYMENT_ORDER_CREATED_QUEUE)
  public void consume(OrderCreatedEvent event) {
    logger.info("Received order-created event: orderId={}", event.orderId());
    paymentService.createPendingPayment(event);
  }
}
