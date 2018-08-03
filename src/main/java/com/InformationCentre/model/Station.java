package com.InformationCentre.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author callummarriage
 */
public class Station {

    private List<Connection> connectedStations;
    private String name;
    private Boolean isFrom;
    private Boolean isTo;
    private Boolean visited;
    private int lastUpdated;
    private int distanceFromPrevious;

    public Station(String name){
        this.name = name;
        this.connectedStations = new ArrayList<>();
        this.isFrom = false;
        this.isTo = false;
        this.visited = false;
        this.lastUpdated = 0;
        this.distanceFromPrevious = 0;
    }

    public void addConnection(Station station, Integer length){
        connectedStations.add(new Connection(length, station));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Connection> getConnections(){
        return this.connectedStations;
    }


    public Boolean getFrom() {
        return isFrom;
    }

    public void setFrom(Boolean from) {
        isFrom = from;
    }

    public Boolean getTo() {
        return isTo;
    }

    public void setTo(Boolean to) {
        isTo = to;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public int getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getDistanceFromPrevious() {
        return distanceFromPrevious;
    }

    public void setDistanceFromPrevious(int distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }
}
