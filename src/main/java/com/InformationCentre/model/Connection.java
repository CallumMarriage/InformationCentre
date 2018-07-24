package com.InformationCentre.model;

/**
 * @author callummarriage
 */
public class Connection {

    public Integer length;
    public Station station;

    public Connection(Integer length, Station station){
        this.length = length;
        this.station = station;
    }

}
