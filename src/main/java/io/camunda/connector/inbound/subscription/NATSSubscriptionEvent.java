package io.camunda.connector.inbound.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * Data model of an event consumed by inbound Connector (e.g. originating from an external system)
 */
public class NATSSubscriptionEvent {
  private final String url;
  private final String topic;
  private final Object message;

  public NATSSubscriptionEvent(String url, String topic, String message) throws JsonProcessingException  {
    this.url = url;
    this.topic = topic;
    ObjectMapper mapper = new ObjectMapper();
    this.message = mapper.readTree(message);
  }

  public String getTopic() {
    return topic;
  }

  public String getUrl() {
    return url;
  }

  public Object getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    System.out.println("checking...");
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NATSSubscriptionEvent that = (NATSSubscriptionEvent) o;
    return Objects.equals(topic, that.topic);
  }

  @Override
  public String toString() {
    return "NATSSubscriptionEvent{" +
        "topic='" + topic + '\'' +
        '}';
  }
}
