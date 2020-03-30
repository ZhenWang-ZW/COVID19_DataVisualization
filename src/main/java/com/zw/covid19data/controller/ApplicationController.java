package com.zw.covid19data.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zw.covid19data.model.DailyCases;
import com.zw.covid19data.model.TotalCases;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ApplicationController {

    private URL FILE_PATH = getClass().getClassLoader().getResource("dataset/IRELAND");
    private List<DailyCases> allCases;
    private TotalCases totalCases;
    @PostConstruct
    public void initialize() throws ParseException, IOException, URISyntaxException {
         if(this.allCases==null || this.allCases.isEmpty()){
             allCases = readAllDailyCases();
         }
        totalCases = TotalCases.getInstance();
        this.setTotalCases();
    }

    @GetMapping("/index.html")
    public String index(Model model) throws URISyntaxException, IOException, ParseException {
//        String name = "Zhen";
//        model.addAttribute("name", name);
        model.addAttribute("cases", this.allCases);
        model.addAttribute("total",this.totalCases);
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
    public RedirectView add_update_data(@ModelAttribute DailyCases _dailyCases){
//        DailyCases _dailyCases = new DailyCases();
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        _dailyCases.setDate(dateFormat.format(date));
        System.out.println(_dailyCases.getDate());
        try {
            this.addUpdateData(_dailyCases);
        }catch (Exception e){
            e.printStackTrace();
        }
//        appendToJsonFile(_dailyCases);
        return new RedirectView("/index.html");
    }

    private List<DailyCases> readAllDailyCases() throws URISyntaxException, IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        List<DailyCases> cases = new ArrayList<DailyCases>(Arrays.asList(mapper.readValue(readJsonData(FILE_PATH.toURI()), DailyCases[].class)));
        return cases;
    }

    private String readJsonData(URI uri) throws IOException, ParseException {
        FileReader reader = new FileReader(uri.getPath());
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader).toString();
    }

    private void addUpdateData(DailyCases newCases) throws URISyntaxException {
        if(!isExist(newCases)){
            //add to allCases
            this.allCases.add(newCases);
        }else{
            for(DailyCases _case : this.allCases){
                if(_case.getDate().equals(newCases.getDate())){
                    _case.setNum_cases(newCases.getNum_cases());
                    _case.setNum_deaths(newCases.getNum_deaths());
                    _case.setNum_recoveries(newCases.getNum_recoveries());
                }
            }
        }
        //appendToJsonFile
        this.appendToJsonFile(FILE_PATH.toURI());
    }

    private boolean appendToJsonFile(URI uri) {
        try {
            FileWriter writer = new FileWriter(uri.getPath());
            String js = JSONArray.toJSONString(this.allCases);
            writer.write(JSONArray.toJSONString(this.allCases));
            writer.flush();
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

    private void setTotalCases(){
        this.totalCases.setTotalInfecte(this.allCases);
        this.totalCases.setTotalDeaths(this.allCases);
        this.totalCases.setTotalRecovires(this.allCases);
        this.totalCases.setRestCases();
    }

    private void reloadData(){

    }
}
