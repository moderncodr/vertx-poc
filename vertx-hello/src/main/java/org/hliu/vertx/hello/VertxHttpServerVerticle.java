package org.hliu.vertx.hello;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class VertxHttpServerVerticle extends AbstractVerticle {

	  // Convenience method so you can run it in your IDE
	  public static void main(String[] args) {
		  Vertx vertx = Vertx.vertx();
		  vertx.deployVerticle(new VertxHttpServerVerticle());
	  }

	  @Override
	  public void start() throws Exception {

	    Router router = Router.router(vertx);

	    router.route().handler(routingContext -> {
	      routingContext.response().putHeader("content-type", "text/html").end("Hello Vertx!");
	    });

	    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	  }
	}
