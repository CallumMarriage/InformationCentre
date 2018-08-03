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
        station1 = station1.trim();
        station2 = station2.trim();
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
