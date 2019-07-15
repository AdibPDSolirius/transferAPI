package com.revolut.transfer.api;

import static spark.Spark.before;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.inject.Inject;

public class Router {

    @Inject
    private Filter filter;

    @Inject
    private Controller controller;

    public void handleRequests() {
        before("/transfer", filter::validateRequest);
        put("/transfer", controller::processTransferRequest);
    }
}
