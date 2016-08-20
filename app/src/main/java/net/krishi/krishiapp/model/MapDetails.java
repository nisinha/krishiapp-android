package net.krishi.krishiapp.model;

import java.util.List;


/**
 * Created by nisinha on 8/20/2016.
 */



public class MapDetails {

    Cordinates center;
    List<Cordinates> polygon;
    double area;
    int zoomLevel;

    public Cordinates getCenter() {
        return center;
    }

    public void setCenter(Cordinates center) {
        this.center = center;
    }

    public List<Cordinates> getPolygon() {
        return polygon;
    }

    public void setPolygon(List<Cordinates> polygon) {
        this.polygon = polygon;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }
}
