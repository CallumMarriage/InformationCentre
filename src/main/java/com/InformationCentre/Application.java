package com.InformationCentre;

import com.InformationCentre.model.*;
import com.InformationCentre.model.FileReader;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author callummarriage
 */
public class Application implements Controller {

    private Network network;
    private Hashtable<String, String> lines;

    public Application(){
        network = new Network();
        lines = new Hashtable<>();
        lines.put("a", "Birmingham -- Dorridge -- Leamington Spa");
        lines.put("b", "Cross City Line");
        lines.put("c", "Birmingham -- Rugby -- Northampton -- London");
        lines.put("d", "Nuneaton -- Coventry");
        lines.put("e", "Watford -- St Albans Abbey");
        lines.put("f", "Bletchley -- Bedford");
        lines.put("g", "Crewe -- Stoke -- Stafford -- London");
        lines.put("h", "Worcester -- Birmingham");
        lines.put("i", "Smethwick Galton Bridge Connections");
        lines.put("j", "Birmingham -- Stratford-upon-Avon");
        lines.put("k", "Bimingham -- Wolverhampton -- Telford -- Shrewsbury");
        lines.put("l", "Birmingham -- Worcester -- Hereford");

    }

    public static void main(String[] args){
       List<String> allStations = FileReader.readCSVFileToStringArray(new File("./src/main/resources/routes/WestMidlandsRailway.csv"));
       Application application = new Application();

       application.generateNetwork(allStations);

       new TUI(application);

    }

    public void generateNetwork(List<String> allStations){
        for(String stationPair : allStations){
            String[] stationPairSplit = stationPair.split(",");
            String trainLine = stationPairSplit[0];
            if(network.getTrainLines().get(trainLine) != null){
                ((TrainLine) network.getTrainLines().get(trainLine)).addStationPair(stationPairSplit[1], stationPairSplit[2], stationPairSplit[3]);
            } else {
                TrainLine newTrainLine = new TrainLine(trainLine);
                newTrainLine.addStationPair(stationPairSplit[1], stationPairSplit[2], stationPairSplit[3]);
                network.getTrainLines().put(trainLine, newTrainLine);
            }
            network.addStationPair(stationPairSplit[1], stationPairSplit[2], stationPairSplit[3]);
        }
    }

    @Override
    public String listAllTermini(String line) {
       return ((TrainLine) network.getTrainLines().get(lines.get(line))).findTermini();
    }

    @Override
    public String listStationsInLine(String line) {
        StringBuilder sb = new StringBuilder();
        // Loop through each line, for each line list all of the stations on that line.
        // this is O(s(The number of stations at each line))

        if (lines.get(line)!= null) {
            if(network.getTrainLines().get(lines.get(line)) != null) {
                TrainLine trainLine = (TrainLine) network.getTrainLines().get(lines.get(line));
                for (Station station : trainLine.getStations()) {
                    sb.append(station.getName());
                    sb.append(", ");
                }
                return sb.toString();
            }
        }
        return "Line is not valid";
    }

    @Override
    public String showPathBetween(String stationA, String stationB) {

        if(stationA.equals("") || stationB.equals("")){
            return "boi";
        }
        // This is O(n(Number of stations in the network)c(Number of Connections at each station))
        Stack<Station> routes = network.findRoute(stationB,(Station) network.getStations().get(stationA));
        if(routes == null){
            return "No route";
        }
        StringBuilder sb = new StringBuilder();
        // This is O(n(Number of stations on the route)
        for(Station station : routes){
            sb.append(station.getName());
            sb.append(" -> ");
        }
        sb.append(stationB);
        return sb.toString();
    }
}
