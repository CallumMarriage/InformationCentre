package com.InformationCentre.util;

import java.io.BufferedReader;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a Utility class for reading Csv files.
 *
 * @author callummarriage
 */
public class FileReader {

    /**
     * This method reads in a csv file and returns the file broken up into lines.
     * It would be more time efficient to generate the network within this method as it would only need to loop through each line once,
     * but this happens before the user enters a request and having a method do multiple things is a bad practise.
     *
     * @param file
     * @return
     */
    public static List<String> readCSVFileToStringArray(File file){
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file));
            String line=null;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }
}
