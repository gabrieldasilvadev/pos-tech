package br.com.postech.soat.commons.infrastructure;

import br.com.postech.soat.commons.domain.DomainEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.joda.time.Instant;

@Entity
@Table(name = "event_store")
public class EventEntity {
  @Id
  private UUID eventId;

  @Column(nullable = false)
  private String aggregateId;

  @Column(nullable = false)
  private String eventType;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String payload;

  @Column(nullable = false)
  private Instant occurredOn;

  @Column(name = "version", nullable = false)
  private int version;

  protected EventEntity() {
  }

  public EventEntity(DomainEvent event) {
    this.eventId = event.getEventId();
    this.aggregateId = event.getAggregateId();
    this.eventType = event.getEventType();
    this.payload = event.toPayload();
    this.occurredOn = event.getOccurredOn();
    this.version = event.getVersion();
  }

  public UUID getEventId() {
    return eventId;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public String getEventType() {
    return eventType;
  }

  public String getPayload() {
    return payload;
  }

  public Instant getOccurredOn() {
    return occurredOn;
  }

  public int getVersion() {
    return version;
  }
}