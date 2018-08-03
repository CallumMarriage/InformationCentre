package com.InformationCentre.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a station (Node)
 *
 * @author callummarriage
 */
public class Station {

    private List<Connection> connectedStations;
    private String name;
    //This is required to show if the station is a source Station (On the left hand side of the csv)
    private Boolean isFrom;
    //This is required to show if the station is a destination Station (On the right hand side of the csv)
    private Boolean isTo;
    //I use the boolean for visited as it is more efficient than to you a stack of all the visited stations.
    private Boolean visited;
    //The last updated field shows which run this station was last edited on, this is because of the state problems caused by using a boolean for visited instead of using a stack
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
