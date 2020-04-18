package org.hliu.vertx.web.auction;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuctionHandler {

	private static final Logger logger = LoggerFactory.getLogger(AuctionHandler.class);
	
    private final AuctionRepository repository;
    private final AuctionValidator validator;

    public AuctionHandler(AuctionRepository repository, AuctionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void handleGetAuction(RoutingContext context) {
        String auctionId = context.request().getParam("id");
        Optional<Auction> auction = this.repository.getById(auctionId);

        if (auction.isPresent()) {
        	context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily(auction.get()));
        } else {
            context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(404)
                .end();
        }
    }

    public void handleChangeAuctionPrice(RoutingContext context) {
        String auctionId = context.request().getParam("id");
        Auction auctionRequest = new Auction(
            auctionId,
            new BigDecimal(context.getBodyAsJson().getString("price"))
        );
        logger.info("Received bidding price: {}.", auctionRequest.getPrice());
        
        if (validator.validate(auctionRequest)) {
        	this.repository.save(auctionRequest);
            context.vertx().eventBus().publish("auction." + auctionId, context.getBodyAsString());

            context.response()
                .setStatusCode(200)
                .end();
        } else {
        	logger.info("Received invalid price: {}.", auctionRequest.getPrice());
            context.response()
                .setStatusCode(422)
                .end();
        }
    }

    public void initAuctionInSharedData(RoutingContext context) {
        String auctionId = context.request().getParam("id");

        Optional<Auction> auction = this.repository.getById(auctionId);
        if(!auction.isPresent()) {
            this.repository.save(new Auction(auctionId));
        }

        context.next();
    }
}
