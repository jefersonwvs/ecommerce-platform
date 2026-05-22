package dev.jefersonwvs.notification.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String EXCHANGE = "ecommerce.exchange";

  // ROUTING KEYS

  public static final String PAYMENT_APPROVED_EVENT = "payment.approved";

  // QUEUES

  public static final String NOTIFICATION_PAYMENT_APPROVED_QUEUE = "notification.payment-approved";

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(EXCHANGE);
  }

  @Bean
  public Queue notificationPaymentApprovedQueue() {
    return new Queue(NOTIFICATION_PAYMENT_APPROVED_QUEUE);
  }

  @Bean
  public Binding notificationPaymentApprovedBinding() {
    return BindingBuilder.bind(notificationPaymentApprovedQueue())
        .to(topicExchange())
        .with(PAYMENT_APPROVED_EVENT);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new JacksonJsonMessageConverter();
  }
}
