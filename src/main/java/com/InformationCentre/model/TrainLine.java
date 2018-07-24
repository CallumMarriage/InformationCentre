package com.InformationCentre.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author callummarriage
 */
public class TrainLine{

    private Collection<Station> stations;
    private String lineName;

    public TrainLine(String lineName){
        this.lineName = lineName;
        this.stations = new ArrayList<>();
    }
    public TrainLine(Collection<Station> stations, String name){
        this.stations = stations;
        this.lineName = name;
    }

    public Collection<Station> getStations() {
        return stations;
    }

    public void setStations(Collection<Station> stations) {
        this.stations = stations;
    }

    public void addStationPair(String station1, String station2, String length ){
        station1 = station1.replace(" ", "");
        station2 = station2.replace(" ", "");
        Station firstMatch = null;
        Station secondMatch = null;

        for(Station station : stations) {
            if(station.getName().equals(station1)){
                firstMatch = station;
            } else if(station.getName().equals(station2)){
                secondMatch = station;
            }
        }

        if(firstMatch != null){
            firstMatch.setTo(true);
            if(secondMatch != null){
                secondMatch.setFrom(true);
                firstMatch.addConnection(secondMatch, Integer.parseInt(length));
                secondMatch.addConnection(firstMatch, Integer.parseInt(length));
                stations.remove(firstMatch);
                stations.remove(secondMatch);
                stations.add(firstMatch);
                stations.add(secondMatch);
                return;
            } else {
                Station newStation = new Station(station2);
                Integer i = Integer.parseInt(length);
                firstMatch.addConnection(newStation, i);
                newStation.addConnection(firstMatch, i);
                newStation.setFrom(true);
                stations.remove(firstMatch);
                stations.add(firstMatch);
                stations.add(newStation);
                return;
            }
        }
        if(secondMatch != null){
            secondMatch.setFrom(true);
            Station newStation = new Station(station1);
            newStation.setTo(true);
            Integer i = Integer.parseInt(length);
            secondMatch.addConnection(newStation, i);
            newStation.addConnection(secondMatch, i);
            stations.remove(secondMatch);
            stations.add(secondMatch);
            stations.add(newStation);
        } else {
            Station newStation = new Station(station1);
            Station newStationB = new Station(station2);
            newStationB.setFrom(true);
            newStation.setTo(true);
            newStation.addConnection(newStationB, Integer.parseInt(length));
            newStationB.addConnection(newStation, Integer.parseInt(length));
            stations.add(newStation);
            stations.add(newStationB);
        }
    }


    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String findTermini(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Station station : stations){
           if((station.getFrom() && !station.getTo()) || (!station.getFrom() && station.getTo())){
                stringBuilder.append(station.getName() + ", ");
           }
        }
        return stringBuilder.toString();
    }
}
