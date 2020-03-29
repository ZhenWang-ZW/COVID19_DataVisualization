package com.zw.covid19data.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zw.covid19data.model.DailyCases;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ApplicationController {

    private URL FILE_PATH = getClass().getClassLoader().getResource("dataset/IRELAND");
    private List<DailyCases> allCases = null;

    @PostConstruct
    public void initialize() throws ParseException, IOException, URISyntaxException {
         if(allCases==null){
             allCases = readAllDailyCases();
         }
    }

    @GetMapping("/index.html")
    public String index(Model model) throws URISyntaxException, IOException, ParseException {
        String name = "Zhen";
        model.addAttribute("name", name);
        model.addAttribute("cases", this.allCases);
        return "index";
    }

    @GetMapping("/add_update.html")
    public String add_update(Model model) {
        DailyCases _dailyCases = new DailyCases();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        _dailyCases.setDate(dateFormat.format(date));
        model.addAttribute("dailyCases", _dailyCases);

        return "add_update";
    }
    @PostMapping("/addORupdate")
    public RedirectView add_update_data(){
        DailyCases _dailyCases = new DailyCases();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        _dailyCases.setDate(dateFormat.format(date));

//        appendToJsonFile(_dailyCases);
        return new RedirectView("/index.html");
    }

    private List<DailyCases> readAllDailyCases() throws URISyntaxException, IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        List<DailyCases> cases = Arrays.asList(mapper.readValue(readJsonData(FILE_PATH.toURI()), DailyCases[].class));

        return cases;
    }

    private String readJsonData(URI uri) throws IOException, ParseException {
        FileReader reader = new FileReader(uri.getPath());
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader).toString();
    }

    private String addUpdateData(DailyCases newCases){
        if(!isExist(newCases)){
            //appendToJsonFile
        }
        return null;
    }

    private boolean appendToJsonFile() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean isExist(DailyCases _case){
        for(DailyCases __case: this.allCases){
            if(_case.getDate().equals(__case.getDate()))
                return true;
        }
        return false;
    }
}
