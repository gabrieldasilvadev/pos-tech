package br.com.postech.soat.commons.domain;

import java.util.Objects;
import java.util.UUID;
import org.joda.time.Instant;

public abstract class DomainEvent {
  private final UUID eventId;
  private final String aggregateId;
  private final Instant occurredOn;
  private final int version;

  protected DomainEvent(UUID eventId, String aggregateId) {
    this.eventId = eventId;
    this.aggregateId = Objects.requireNonNull(aggregateId);
    this.occurredOn = Instant.now();
    this.version = 1;
  }

  public UUID getEventId() {
    return eventId;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public Instant getOccurredOn() {
    return occurredOn;
  }

  public int getVersion() {
    return version;
  }

  public abstract String getEventType();

  public abstract String toPayload();

  protected String baseMetadata() {
    return String.format(
      "\"eventId\":\"%s\",\"aggregateId\":\"%s\",\"occurredOn\":\"%s\",\"version\":%d",
      eventId, aggregateId, occurredOn, version
    );
  }
}