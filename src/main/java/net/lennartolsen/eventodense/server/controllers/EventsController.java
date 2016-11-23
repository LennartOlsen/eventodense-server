package net.lennartolsen.eventodense.server.controllers;

import com.google.gson.Gson;
import net.lennartolsen.eventodense.server.models.event.Event;
import net.lennartolsen.eventodense.server.models.event.EventSqlRepository;
import net.lennartolsen.eventodense.server.models.point.Point;
import net.lennartolsen.eventodense.server.models.point.PointSqlRepository;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

/**
 * Created by lennartolsen on 25/10/2016.
 */
public class EventsController {

    public static String get(Request req, Response res){
        System.out.println("EVENTS/get");

        EventSqlRepository repos = new EventSqlRepository();
        Gson jsoner = new Gson();

        ArrayList<Event> events = repos.get(0,0);

        String json;
        try {
            json = jsoner.toJson(events);
        } catch(Exception e){
            System.out.println(e);
            res.status(400);
            return "error";
        }

        return json;
    }

    public static String post(Request req, Response res){
        System.out.println("Events/post");
        String body = req.body();
        Gson jsoner = new Gson();

        Event event;

        try {
            event = jsoner.fromJson(body, Event.class);
        } catch(Exception e){
            System.out.println(e);
            res.status(400);
            return "decode error, events come singularily";
        }

        EventSqlRepository repos = new EventSqlRepository();

        if(repos.save(event)){
            res.status(200);
            return jsoner.toJson(event);
        } else {
            res.status(500);
            return "We done fuckedup";
        }
    }

    public static String getId(Request req, Response res){
        System.out.println("EVENTS/get/Id");

        EventSqlRepository repos = new EventSqlRepository();
        Gson jsoner = new Gson();

        String id = req.params(":id");

        ArrayList<Event> events = repos.get(id);

        String json;
        try {
            json = jsoner.toJson(events);
        } catch(Exception e){
            System.out.println(e);
            res.status(400);
            return "error";
        }

        return json;
    }

    public static String getPoints(Request req, Response res){
        System.out.println("EVENTS/get/points");

        PointSqlRepository repos = new PointSqlRepository();
        Gson jsoner = new Gson();

        String id = req.params(":id");

        ArrayList<Point> events = repos.getPoints(0,0, id);

        String json;
        try {
            json = jsoner.toJson(events);
        } catch(Exception e){
            System.out.println(e);
            res.status(400);
            return "error";
        }

        return json;
    }
}
