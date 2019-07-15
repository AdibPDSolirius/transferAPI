package com.revolut.transfer.api;

import static spark.Spark.before;
import static spark.Spark.post;

import com.google.inject.Inject;

public class Router {

    @Inject
    private Filter filter;

    @Inject
    private Handler handler;

    public void handleRequests() {
        before("/transfer", filter::validateRequest);
        post("/transfer", handler::processTransferRequest);
    }
}
