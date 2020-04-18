package org.hliu.vertx.hello;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class VertxHttpClientVerticle extends AbstractVerticle {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new VertxHttpClientVerticle());
	}
	
	@Override
    public void start() throws Exception {
		WebClient client = WebClient.create(vertx);

	    client.get(8080, "localhost", "/").send(ar -> {
	      if (ar.succeeded()) {
	        HttpResponse<Buffer> response = ar.result();
	        System.out.println("HTTP response with status (" + response.statusCode() + ") with data: " +
	          response.body().toString("UTF-8"));
	      } else {
	        ar.cause().printStackTrace();
	      }
	});
    }

}
