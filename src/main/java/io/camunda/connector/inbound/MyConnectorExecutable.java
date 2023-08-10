package io.camunda.connector.inbound;

import io.camunda.connector.api.annotation.InboundConnector;
import io.camunda.connector.api.inbound.InboundConnectorContext;
import io.camunda.connector.api.inbound.InboundConnectorExecutable;
import io.camunda.connector.inbound.subscription.NATSSubscription;
import io.camunda.connector.inbound.subscription.NATSSubscriptionEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@InboundConnector(name = "NATSINBOUNDCONNECTOR", type = "io.camunda:natsinbound:1")
public class MyConnectorExecutable implements InboundConnectorExecutable {

  private NATSSubscription subscription;
  private InboundConnectorContext connectorContext;
  private ExecutorService executorService;
  public CompletableFuture<?> future;

  @Override
  public void activate(InboundConnectorContext connectorContext) {
    MyConnectorProperties props = connectorContext.bindProperties(MyConnectorProperties.class);
    this.connectorContext = connectorContext;
    this.executorService = Executors.newSingleThreadExecutor();

    this.future =
            CompletableFuture.runAsync(
                    () -> {
                      new NATSSubscription(props.getUrl(), props.getTopic(), props.getMessage(), props.getPollingInterval(), this::onEvent);
                    },
                    this.executorService);
  }

  @Override
  public void deactivate() {
    subscription.stop();
  }

  private void onEvent(NATSSubscriptionEvent rawEvent) {

    MyConnectorEvent connectorEvent = new MyConnectorEvent(rawEvent);
    connectorContext.correlate(connectorEvent);
  }
}
