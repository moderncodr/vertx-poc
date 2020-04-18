package org.hliu.vertx.web.home;

import org.hliu.vertx.web.mvc.PageHandler;
import org.hliu.vertx.web.mvc.ProcessResult;

import io.vertx.ext.web.RoutingContext;

public class HomeHandler extends PageHandler<HomePage>{

	@Override
	protected HomePage getPage(RoutingContext context) {
		return new HomePage();
	}

	@Override
	protected ProcessResult process(RoutingContext context, HomePage page) {
		return ProcessResult.OK_RESULT;
	}

}
