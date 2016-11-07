package net.lennartolsen.eventodense.server;

import net.lennartolsen.eventodense.server.controllers.PointsController;
import spark.Service;

import static spark.Service.ignite;

/**
 * Created by lennartolsen on 24/10/2016.
 */

/**
 * START WITH PACKAGE
 * mvn package
 * AND EXECUTE WITH
 * mvn exec:java "-Dexec.mainClass=net.lennartolsen.eventodense.server.Main"
 * Build with DEPS:
 * mvn assembly:assembly -DdescriptorId=jar-with-dependencie
 */
public class Main {
    public static void main(String[] args) {
        Service http = ignite()
                .port(8080)
                .threadPool(20);

        http.get(
                "/hello",
                (req, res) -> "Hello World"
        );
        http.get(
                "/points",
                (req, res) -> PointsController.get(req, res)
        );
        http.post(
                "/points",
                (req, res) -> PointsController.post(req, res)
        );
    }
}
