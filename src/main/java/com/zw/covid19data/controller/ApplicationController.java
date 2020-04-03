package com.zw.covid19data.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zw.covid19data.model.DailyCases;
import com.zw.covid19data.model.ListData;
import com.zw.covid19data.model.TotalCases;
import com.zw.covid19data.utils.HelperClass;
import org.json.simple.JSONArray;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ApplicationController {
    private String FILE_PATH = "src/main/resources/dataset/IRELAND";
    //private URL FILE_PATH = getClass().getClassLoader().getResource("dataset/IRELAND");
    private List<DailyCases> allCases;
    private TotalCases totalCases;
    private DailyCases latestUpdate;
    private ListData listData;

    @PostConstruct
    public void initialize() throws ParseException, IOException {
         if(this.allCases==null || this.allCases.isEmpty()){
             this.allCases = readAllDailyCases();
         }
        this.totalCases = TotalCases.getInstance();
        this.setTotalCases();

        this.latestUpdate = HelperClass.getLatest(this.allCases);

        this.listData = new ListData();
        for(DailyCases _case:this.allCases){
            this.listData.updateListData(_case);
        }
    }

    @GetMapping("/index.html")
    public String index(Model model) throws IOException, ParseException {
        model.addAttribute("cases", this.allCases);
        model.addAttribute("total",this.totalCases);
        model.addAttribute("latest",this.latestUpdate);
        model.addAttribute("listData", this.listData);
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
        try {
            this.addUpdateData(_dailyCases);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.reloadData();
        return new RedirectView("/index.html");
    }

    private List<DailyCases> readAllDailyCases() throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        List<DailyCases> cases = new ArrayList<DailyCases>(Arrays.asList(mapper.readValue(HelperClass.readFromFile(this.FILE_PATH), DailyCases[].class)));
        Collections.sort(cases);
        return cases;
    }

    private void addUpdateData(DailyCases newCases) {
        if(!isExist(newCases)){
            //add to allCases
            this.allCases.add(newCases);
            this.listData.updateListData(newCases);
        }else{
            for(DailyCases _case : this.allCases){
                if(_case.getDate().equals(newCases.getDate())){
                    _case.setNum_cases(newCases.getNum_cases());
                    _case.setNum_deaths(newCases.getNum_deaths());
                    _case.setNum_recoveries(newCases.getNum_recoveries());
                }
            }
        }
        Collections.sort(this.allCases);
        //appendToJsonFile
        String strData = JSONArray.toJSONString(this.allCases);
        HelperClass.writeToFile(strData, this.FILE_PATH);
    }

    private boolean isExist(DailyCases _case){
        for(DailyCases __case: this.allCases){
            if(_case.getDate().equals(__case.getDate()))
                return true;
        }
        return false;
    }

    private void setTotalCases(){
        this.totalCases.setTotalInfect(this.allCases);
        this.totalCases.setTotalDeaths(this.allCases);
        this.totalCases.setTotalRecoveries(this.allCases);
        this.totalCases.setRestCases();
    }

    private void reloadData(){
        this.totalCases = TotalCases.getInstance();
        this.setTotalCases();

        this.latestUpdate = HelperClass.getLatest(this.allCases);
    }
}
