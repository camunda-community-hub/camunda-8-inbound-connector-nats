package io.camunda.connector.inbound;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Configuration properties for inbound Connector
 */
public class MyConnectorProperties {

  private String url;
  private String topic;
  private String message;
  private String pollingInterval;

  public String getUrl() {
    return url;
  }
  public String getTopic() {
    return topic;
  }
  public String getMessage() {
    return message;
  }
  public String getPollingInterval() {
    return pollingInterval;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  public void setTopic(String topic) {
    this.topic = topic;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public void setPollingInterval(String pollingInterval) {
    this.pollingInterval = pollingInterval;
  }

  @Override
  public String toString() {
    return "MyConnectorProperties{" +
        "url='" + url + '\'' +
        "topic='" + topic + '\'' +
        "message='" + message + '\'' +
        "polling interval='" + pollingInterval + '\'' +
        '}';
  }
}
