package br.com.postech.soat.commons.domain;

import java.util.Objects;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class Identifier extends ValueObject {
  private final UUID value;

  protected Identifier(UUID value) {
    Objects.requireNonNull(value, "value of DomainIdentifier cannot be null");
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
