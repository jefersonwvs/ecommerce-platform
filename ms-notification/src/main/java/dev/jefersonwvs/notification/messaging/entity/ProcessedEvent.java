package dev.jefersonwvs.notification.messaging.entity;

import jakarta.persistence.*;
import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tbl_processed_event")
public class ProcessedEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String eventId;

  @Column(nullable = false)
  @CreationTimestamp
  private Instant processedAt;

  protected ProcessedEvent() {}

  public ProcessedEvent(String eventId) {
    this.eventId = eventId;
  }

  public Long getId() {
    return id;
  }

  public String getEventId() {
    return eventId;
  }

  public Instant getProcessedAt() {
    return processedAt;
  }
}
