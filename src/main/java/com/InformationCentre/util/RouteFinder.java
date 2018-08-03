package com.InformationCentre.util;

import com.InformationCentre.model.Connection;
import com.InformationCentre.model.Station;

import java.util.Stack;

/**
 * Created by callummarriage on 03/08/2018.
 */
public class RouteFinder {

    private int duration;

    public Stack<Station> findRoute(Station destination, Station source, Integer run){

        duration = 0;


        Station next = source;
        Stack<Station> route = new Stack<>();

        while (next != null){

            Station station = next;
            next = null;

            if(station.getName().equals(destination.getName())){
                return route;
            }

            if(!station.getVisited()) {
                Boolean notVisited = true;
                int i = 0;
                while(notVisited) {
                    if(i >= station.getConnections().size()){
                        if(route.size() > 1){
                            route.remove(station);
                            Station parent = route.get(route.size() -1);
                            duration -= station.getDistanceFromPrevious();
                            if(parent.getName().equals(station.getName())){
                                route.remove(parent);
                                parent = route.get(route.size() -1);
                            }
                            next = parent;
                            parent.setVisited(false);
                            station.setVisited(true);
                            notVisited = false;
                        } else {
                            return null;
                        }
                    } else {
                        Connection connection = station.getConnections().get(i);
                        Station nextStation = station.getConnections().get(i).station;

                        if (!nextStation.getVisited()) {

                            if(nextStation.getLastUpdated() !=run ){
                                nextStation.setVisited(false);
                                nextStation.setLastUpdated(run);
                            }
                            nextStation.setDistanceFromPrevious(connection.length);
                            duration += connection.length;
                            if(route.size() > 0) {
                                if (route.peek().getName().equals(station.getName())) {
                                    route.pop();
                                }
                            }
                            route.add(station);
                            station.setVisited(true);
                            notVisited = false;
                            next = nextStation;
                        } else {
                            i++;
                        }
                    }

                }
            }
        }
        return null;
    }


    public Integer getDuration(){
        return this.duration;
    }
}
