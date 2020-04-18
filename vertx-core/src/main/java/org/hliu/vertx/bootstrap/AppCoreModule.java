package org.hliu.vertx.bootstrap;

import org.hliu.vertx.auction.TradingService;
import org.hliu.vertx.auction.TradingServiceImpl;

import com.google.inject.AbstractModule;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(TradingService.class).to(TradingServiceImpl.class);
		
	}


}
