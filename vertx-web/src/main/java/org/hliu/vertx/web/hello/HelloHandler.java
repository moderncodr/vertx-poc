package org.hliu.vertx.web.hello;

import org.hliu.vertx.web.mvc.PageHandler;
import org.hliu.vertx.web.mvc.ProcessResult;

import io.vertx.ext.web.RoutingContext;

public class HelloHandler extends PageHandler<HelloPage>{

	@Override
	protected HelloPage getPage(RoutingContext context) {
		return new HelloPage();
	}

	@Override
	protected ProcessResult process(RoutingContext context, HelloPage page) {
		String id = context.pathParam("id");
		
		return ProcessResult.OK_RESULT;
	}

}
