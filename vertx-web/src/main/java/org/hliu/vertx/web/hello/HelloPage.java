package org.hliu.vertx.web.hello;

import org.hliu.vertx.web.mvc.AbstractPage;

public class HelloPage extends AbstractPage {

	private static final String TEMPLATE = "hello.html";

	public HelloPage() {
		super(TEMPLATE);
	}

}
