package com.InformationCentre.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Model for Train Line object
 *
 * @author callummarriage
 */
public class TrainLine{

    private Collection<Station> stations;
    private String lineName;
    private String allStationsAsString;

    public TrainLine(String lineName){
        this.lineName = lineName;
        this.stations = new ArrayList<>();
        allStationsAsString = "";
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

    /**
     * Adds a connection between two station objects, this is an important part of modelling a Graph Structure.
     * This code is similar to that in the Network class, this is because it is performing a similar action but there are minor differences
     * and for the sake of take of time and effeciency I have 2 versions of this code.
     *
     * @param station1
     * @param station2
     * @param length
     */
    public void addStationPair(String station1, String station2, String length ){
        station1 = station1.trim();
        station2 = station2.trim();
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
                if(stations.size() > 0){
                    allStationsAsString += " <->";
                } else {
                    allStationsAsString += "Stations: ";
                }
                allStationsAsString += newStation.getName();
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
            if(stations.size() > 0){
                allStationsAsString += " <->";
            } else {
                allStationsAsString += "Stations: ";
            }

            allStationsAsString += newStation.getName();
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
            if(stations.size() > 0){
                allStationsAsString += " <->";
            } else {
                allStationsAsString += "Stations: ";
            }
            allStationsAsString += newStation.getName();
            allStationsAsString += " <->" + newStationB.getName();

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

    /**
     * This method goes through all the stations on a line and checks if they appeared as both a source and destination station in the csv,
     * if they are both then they are not a termini.
     *
     * @return string of termini
     */
    public String findTermini(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Station station : stations){
           if((station.getFrom() && !station.getTo()) || (!station.getFrom() && station.getTo())){
                stringBuilder.append(station.getName() + ", ");
           }
        }
        return stringBuilder.toString();
    }

    public String getAllStationsAsString() {
        return allStationsAsString;
    }

    public void setAllStationsAsString(String allStationsAsString) {
        this.allStationsAsString = allStationsAsString;
    }
}
