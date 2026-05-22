package dev.jefersonwvs.payment.messaging.config;

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

  static final String ORDER_CREATED_EVENT = "order.created";

  static final String PAYMENT_APPROVED_EVENT = "payment.approved";

  // QUEUES

  public static final String PAYMENT_ORDER_CREATED_QUEUE = "payment.order-created";

  public static final String ORDER_PAYMENT_APPROVED_QUEUE = "order.payment-approved";

  public static final String NOTIFICATION_PAYMENT_APPROVED_QUEUE = "notification.payment-approved";

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(EXCHANGE);
  }

  @Bean
  public Queue paymentOrderCreatedQueue() {
    return new Queue(PAYMENT_ORDER_CREATED_QUEUE);
  }

  @Bean
  public Binding paymentOrderCreatedBinding() {
    return BindingBuilder.bind(paymentOrderCreatedQueue())
        .to(topicExchange())
        .with(ORDER_CREATED_EVENT);
  }

  @Bean
  public Queue orderPaymentApprovedQueue() {
    return new Queue(ORDER_PAYMENT_APPROVED_QUEUE);
  }

  @Bean
  public Binding orderPaymentApprovedBinding() {
    return BindingBuilder.bind(orderPaymentApprovedQueue())
        .to(topicExchange())
        .with(PAYMENT_APPROVED_EVENT);
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
