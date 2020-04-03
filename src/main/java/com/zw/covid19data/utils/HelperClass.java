package com.zw.covid19data.utils;

import com.zw.covid19data.model.DailyCases;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HelperClass {
    public static DailyCases getLatest(List<DailyCases> _list){
        return _list.get(_list.size()-1);
    }

    public static String readFromFile(String path) throws IOException, ParseException {
        FileReader reader = new FileReader(path);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader).toString();
    }

    public static Boolean writeToFile(String data, String path){
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(data);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
