package net.lennartolsen.eventodense.server.models.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lennartolsen on 21/11/2016.
 */
public class Event {
    private static final String TYPE = "event";

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lng")
    @Expose
    private double lng;

    @SerializedName("startTime")
    @Expose
    private int startTime;

    @SerializedName("endTime")
    @Expose
    private int endTime;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("imageId")
    @Expose
    private String imageId;

    @SerializedName("radius")
    @Expose
    private int radius;

    @SerializedName("color")
    @Expose
    private String color;

    public Event(){}

    public Event(String id, double lat, double lng, int start, int end, String name, String description, String imageId, int radius, String color) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.startTime = start;
        this.endTime = end;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.radius = radius;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int start_time) {
        this.startTime = start_time;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int end_time) {
        this.endTime = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
