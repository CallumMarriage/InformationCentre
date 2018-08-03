package com.InformationCentre;

import com.InformationCentre.model.*;
import com.InformationCentre.util.*;
import com.InformationCentre.view.TUI;

import java.io.*;
import java.util.*;

/**
 * Main class used to trigger the TUI and to communicate with the TUI.
 * I have mapped the network into two structures, both of which are Graphs, the first being a HashTable of TrainLines, the second being A HashTable of Stations.
 * Every TrainLine contains a collection of Stations (Nodes) all of which are connected to each other using a connection (Edge).
 * Every Station (Node in the Hash Table is connected to each other station as defined in the CSV using a Connection (Edge).
 *
 * The reason I split the structures into 2 is because it is more time efficient to find every element in a trainline if it is in its own structure.
 * It also means that when you want to use the Find Route method on TrainLine, the scope is fixed to just stations on that line.
 *
 * @author callummarriage
 */
public class Application implements Controller {

    private Network network;
    private Hashtable<String, String> lines;
    private int run;

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
        this.run = 0;

    }

    public static void main(String[] args){

       List<String> allStations = com.InformationCentre.util.FileReader.readCSVFileToStringArray(new File("./src/main/resources/routes/WestMidlandsRailway.csv"));
       Application application = new Application();

       application.generateNetwork(allStations);

       new TUI(application);

    }

    /**
     * Generates the Network, populates the Train Line Hash Table as well as the Stations Hashtable.
     *
     * @param allStations every line in the csv
     */
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

        run++;
        RouteFinder routeFinder = new RouteFinder();

        if(network.getTrainLines().get(lines.get(line)) != null) {
            TrainLine trainLine = (TrainLine) network.getTrainLines().get(lines.get(line));

            ArrayList<Station> stations = (ArrayList<Station>) trainLine.getStations();

            routeFinder.findRoute(stations.get(0), stations.get(stations.size() - 1), run);

            sb.append("Duration: ");
            sb.append(routeFinder.getDuration());
            sb.append("\n");

            sb.append(trainLine.getAllStationsAsString());

            return sb.toString();
        }
        return "Line is not valid";
    }

    @Override
    public String showPathBetween(String stationA, String stationB) {

        run++;

        if(network.getStations().get(stationA) == null || network.getStations().get(stationB) == null) {
            return "Station does not exist";
        }
        RouteFinder routeFinder = new RouteFinder();
        // This is O(n(Number of stations in the network)c(Number of Connections at each station))
        Stack<Station> routes = routeFinder.findRoute((Station) network.getStations().get(stationB),(Station) network.getStations().get(stationA), run);
        if(routes == null){
            return "No route";
        }

        System.out.println("Duration : " + routeFinder.getDuration() + " mins.");

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
