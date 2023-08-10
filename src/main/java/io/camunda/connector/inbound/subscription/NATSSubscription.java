package io.camunda.connector.inbound.subscription;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;
import io.nats.client.Nats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class NATSSubscription {

    private final static Logger LOG = LoggerFactory.getLogger(NATSSubscription.class);


    public NATSSubscription(String url, String topic, String message, String pollingInterval, Consumer<NATSSubscriptionEvent> callback) {
        LOG.info("Activating NATS subscription");
        // listen to NATS topic
        try {
            Connection natsConnection = Nats.connect(url);
            Subscription subscription = natsConnection.subscribe(topic);

            boolean neverEnding = true;

            while (neverEnding) {
                Message nextMessage = subscription.nextMessage(Long.parseLong(pollingInterval) * 1000);
                if (nextMessage != null) {
                    LOG.info("got message "+ new String(nextMessage.getData(), StandardCharsets.UTF_8));
                    NATSSubscriptionEvent nse = new NATSSubscriptionEvent(url, topic, new String(nextMessage.getData(), StandardCharsets.UTF_8));
                    callback.accept(nse);
                } else {
                    LOG.info("no messages");
                }
            }
        } catch (Exception e) {
            LOG.error("Problem with connector "+e);
        }

    }

    public void stop() {
        LOG.info("Deactivating NATS subscription");
    }

}
