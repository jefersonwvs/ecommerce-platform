package dev.jefersonwvs.notification.messaging.consumer;

import com.rabbitmq.client.Channel;
import dev.jefersonwvs.notification.messaging.config.RabbitMQConfig;
import dev.jefersonwvs.notification.messaging.entity.ProcessedEvent;
import dev.jefersonwvs.notification.messaging.event.PaymentApprovedEvent;
import dev.jefersonwvs.notification.messaging.repository.ProcessedEventRepository;
import dev.jefersonwvs.notification.service.EmailService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentApprovedConsumer {

  private static final Logger logger = LoggerFactory.getLogger(PaymentApprovedConsumer.class);

  private final ProcessedEventRepository processedEventRepository;
  private final EmailService emailService;

  public PaymentApprovedConsumer(
      ProcessedEventRepository processedEventRepository, EmailService emailService) {
    this.processedEventRepository = processedEventRepository;
    this.emailService = emailService;
  }

  @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_PAYMENT_APPROVED_QUEUE)
  public void consume(PaymentApprovedEvent event, Channel channel, Message message)
      throws IOException {
    logger.info("Received payment-approved event. orderId={}", event.orderId());
    try {
      boolean alreadyProcessed = processedEventRepository.existsByEventId(event.eventId());
      if (alreadyProcessed) {
        logger.info(
            "The approved payment event has already been processed: eventId={}", event.eventId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        return;
      }

      emailService.sendApprovedPaymentEmail("jeferson@mail.com", event.orderId());
      processedEventRepository.save(new ProcessedEvent(event.eventId()));

      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    } catch (Exception e) {
      logger.error("Failed to process payment-approved event: eventId={}", event.eventId(), e);
      channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }
  }
}
