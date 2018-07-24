package com.InformationCentre.model;

import java.util.*;

/**
 * @author callummarriage
 */
public class Network<T>{

    private Hashtable<String, TrainLine> trainLines;

    private Hashtable<String, Station> stations;

    public Network(){
        trainLines = new Hashtable<>();
        stations = new Hashtable<>();
    }

    public void addStationPair(String station1, String station2, String length){
        station1 = station1.replace(" ", "");
        station2 = station2.replace(" ", "");
        Station firstMatch = null;
        Station secondMatch = null;

        if(stations.get(station1) != null){
            firstMatch = stations.get(station1);
        }
        if(stations.get(station2) != null){
            secondMatch = stations.get(station2);
        }

        if(firstMatch != null){
            for(Connection connection : firstMatch.getConnections()){
                if(connection.station.getName().equals(station2)){
                    return;
                }
            }

            firstMatch.setTo(true);
            if(secondMatch != null){
                secondMatch.setFrom(true);
                firstMatch.addConnection(secondMatch, Integer.parseInt(length));
                secondMatch.addConnection(firstMatch, Integer.parseInt(length));
                stations.remove(station1);
                stations.remove(station2);
                stations.put(station1, firstMatch);
                stations.put(station2, secondMatch);
                return;
            } else {
                Station newStation = new Station(station2);
                Integer i = Integer.parseInt(length);
                firstMatch.addConnection(newStation, i);
                newStation.addConnection(firstMatch, i);
                newStation.setFrom(true);
                stations.remove(station1);
                stations.put(station1, firstMatch);
                stations.put(station2, newStation);
                return;
            }
        }
        if(secondMatch != null){
            for(Connection connection : secondMatch.getConnections()){
                if(connection.station.getName().equals(station1)){
                    return;
                }
            }
            secondMatch.setFrom(true);
            Station newStation = new Station(station1);
            newStation.setTo(true);
            Integer i = Integer.parseInt(length);
            secondMatch.addConnection(newStation, i);
            newStation.addConnection(secondMatch, i);
            stations.remove(station2);
            stations.put(station2, secondMatch);
            stations.put(station1, newStation);
        } else {
            Station newStation = new Station(station1);
            Station newStationB = new Station(station2);
            newStationB.setFrom(true);
            newStation.setTo(true);
            newStation.addConnection(newStationB, Integer.parseInt(length));
            newStationB.addConnection(newStation, Integer.parseInt(length));
            stations.put(station1, newStation);
            stations.put(station2, newStationB);
        }
    }

    public Stack<Station> findRoute(String destination, Station source){
        LinkedList<Station> next = new LinkedList();
        Stack<Station> route = new Stack<>();
        next.add(source);

        while (!next.isEmpty()){

            Station station = next.remove();

            if(station.getName().equals(destination)){
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
                            if(parent.getName().equals(station.getName())){
                                route.remove(parent);
                                parent = route.get(route.size() -1);
                            }
                            next.add(parent);
                            parent.setVisited(false);
                            station.setVisited(true);
                            notVisited = false;
                        } else {
                            return null;
                        }
                    } else {
                        for(Connection connection : station.getConnections()){
                            System.out.println(connection.station.getName());
                        }
                        Station nextStation = station.getConnections().get(i).station;
                        if (!nextStation.getVisited()) {


                            if(route.size() > 0) {
                                if (route.peek().getName().equals(station.getName())) {
                                    route.pop();
                                }
                            }
                            route.add(station);
                            station.setVisited(true);
                            notVisited = false;
                            next.add(nextStation);
                        } else {
                            i++;
                        }
                    }

                }
            }
        }
        return null;
    }

    public Hashtable<String, Station> getStations() {
        return stations;
    }

    public void setStations(Hashtable<String, Station> stations) {
        this.stations = stations;
    }


    public Hashtable<String, TrainLine> getTrainLines() {
        return trainLines;
    }

    public void setTrainLines(Hashtable<String, TrainLine> trainLines) {
        this.trainLines = trainLines;
    }
}
