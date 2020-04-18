package org.hliu.vertx.messaging;

import io.vertx.core.Vertx;

public class LocalRunner {

	public static void main(String[] args) {
		// Run both receiver and sender in the same JVM
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Receiver());
		vertx.deployVerticle(new Sender());
	}

}
