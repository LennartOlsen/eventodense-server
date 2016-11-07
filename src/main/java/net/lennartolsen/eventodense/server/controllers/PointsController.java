package net.lennartolsen.eventodense.server.controllers;

import com.google.gson.Gson;
import net.lennartolsen.eventodense.server.models.point.Point;
import net.lennartolsen.eventodense.server.models.point.PointSqlRepository;
import spark.Request;
import spark.Response;

/**
 * Created by lennartolsen on 25/10/2016.
 */
public class PointsController extends BaseHTTPController {

    public static String get(Request req, Response res){
        return "[]";
    }

    public static String post(Request req, Response res){
        String body = req.body();
        res.header("Content-Type", "application/json");
        Gson jsoner = new Gson();
        Point[] points;
        try {
            points = jsoner.fromJson(body, Point[].class);
        } catch(Exception e){
            System.out.println(e);
            res.status(400);
            return "error";
        }

        PointSqlRepository repos = new PointSqlRepository();

        if(repos.saveBatch(points)){
            res.status(200);
            return jsoner.toJson(points);
        } else {
            res.status(500);
            return "We done fuckedup";
        }
    }
}
