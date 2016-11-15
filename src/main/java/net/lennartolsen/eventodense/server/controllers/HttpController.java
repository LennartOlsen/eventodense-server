package net.lennartolsen.eventodense.server.controllers;

import spark.Request;
import spark.Response;

/**
 * Created by lennartolsen on 15/11/2016.
 */
public class HttpController {

    public static String options(Request req, Response res){
        System.out.println("*/options");
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Methods","POST, PUT, GET, OPTIONS");
        return "OK";
    }
}
