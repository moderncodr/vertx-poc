package org.hliu.vertx.web;

import org.hliu.vertx.bootstrap.AppCoreModule;
import org.hliu.vertx.web.hello.HelloHandler;
import org.hliu.vertx.web.home.HomeHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class WebApp extends AbstractVerticle {

	private static final int DEFAULT_PORT = 8080;

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		Vertx vertx = Vertx.vertx();
		run(vertx, port);

	}

	@Override
	public void start() throws Exception {

		run(vertx, DEFAULT_PORT);
	}

	private static void run(Vertx vertx, int port) {

		Router router = Router.router(vertx);

		Injector injector = Guice.createInjector(new AppCoreModule());

		// This cookie handler will be called for all routes
		router.route().handler(CookieHandler.create());
		router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

		// Serve the dynamic pages
		router.get("/").handler(injector.getInstance(HomeHandler.class));
		router.get("/hi").handler(injector.getInstance(HelloHandler.class));
		router.route().handler(BodyHandler.create().setBodyLimit(10485760));
		// router.post("/contact").handler(injector.getInstance(ProcessContactHandler.class));

		// Serve the static pages
		router.route().handler(StaticHandler.create("static"));
		
		vertx.createHttpServer().requestHandler(router::accept).listen(port);
	}
}
