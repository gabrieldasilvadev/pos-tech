package br.com.postech.soat.commons.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<TYPE, ID extends Identifier<TYPE>> {
  private final ID id;
  private final List<DomainEvent> events = new ArrayList<>();

  protected AggregateRoot(ID id) {
    this.id = id;
  }

  public ID getId() {
    return id;
  }

  @SuppressWarnings("unchecked")
  protected <DomainIdentifier extends Identifier<TYPE>> Identifier<TYPE> generateId() {
    return (Identifier<TYPE>) this.id.generate();
  }

  protected void applyChange(DomainEvent event) {
    events.add(event);
  }

  public List<DomainEvent> getEvents() {
    return Collections.unmodifiableList(events);
  }

  public void clearEvents() {
    events.clear();
  }
}
