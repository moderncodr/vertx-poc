package org.hliu.vertx.messaging;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Sender extends AbstractVerticle {

  public static void main(String[] args) {
	  Runner.runClusteredExample(Sender.class);
  }

  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    // Send a message every second
    vertx.setPeriodic(1000, v -> eb.publish("news-feed", "Stupid news!"));
  }
}
