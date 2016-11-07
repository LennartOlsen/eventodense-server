package net.lennartolsen.eventodense.server.controllers;

import spark.Response;

/**
 * Created by lennartolsen on 01/11/2016.
 */
public class BaseHTTPController {
    /**
     * Call me to set base headers (should be handled by some middleware)
     * @param res
     * @return
     */
    protected static Response injectBaseHeaders(Response res){
        res.header("Content-Type", "Application/JSON");
        return res;
    }
}
