package net.lennartolsen.eventodense.server;

import net.lennartolsen.eventodense.server.controllers.EventsController;
import net.lennartolsen.eventodense.server.controllers.PointsController;
import spark.Service;

import static spark.Service.ignite;
import static spark.Spark.after;
import static spark.Spark.before;

/**
 * Created by lennartolsen on 24/10/2016.
 */

/**
 * START WITH PACKAGE
 * mvn package
 * AND EXECUTE WITH
 * mvn exec:java "-Dexec.mainClass=net.lennartolsen.eventodense.server.Main"
 * Build with DEPS:
 * mvn assembly:assembly -DdescriptorId=jar-with-dependencies
 * Exec with
 * java -cp target/event-odense-server-0.2-jar-with-dependencies.jar net.lennartolsen.eventodense.server.Main
 */
public class Main {
    public static void main(String[] args) {
        Service http = ignite()
                .port(8080)
                .threadPool(20);

        System.out.println("We are up on port 8080");

        http.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods","POST, PUT, GET, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type");
            response.header("Content-Type", "application/json");
        });

        http.options("/*",
                (req,res) -> "OK"
        );

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

        http.get(
                "/events",
                (req, res) -> EventsController.get(req, res)
        );
        http.get(
                "/events/:id",
                (req, res) -> EventsController.getId(req, res)
        );
        http.get(
                "/events/:id/points",
                (req, res) -> EventsController.getPoints(req, res)
        );
        http.post(
                "/events",
                (req, res) -> EventsController.post(req, res)
        );
    }
}
