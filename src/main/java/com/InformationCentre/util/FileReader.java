package com.InformationCentre.util;

import java.io.BufferedReader;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author callummarriage
 */
public class FileReader {

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
