package com.InformationCentre.util;

import com.InformationCentre.model.Connection;
import com.InformationCentre.model.Station;

import java.util.Stack;

/**
 * Utility for finding the route between two stations, this class should be static but having to return both the duration and the Route means that it needs to have state.
 * An alternative would be to loop through the stack in the Application class but that would add additional time onto the process.
 *
 * @author callummarriage
 */
public class RouteFinder {

    private int duration;

    /**
     * This method using a Stack and the algorithm Depth First Search to locate the destination station and record the route to it.
     * It navigates all of the stations in either a train line or a hashtable of stations by using the built in links between stations that they are connected to.
     * This method records the Time it takes to get from the source to the destination using the Global field duration.
     *
     *
     * @param destination
     * @param source
     * @param run
     * @return
     */
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
