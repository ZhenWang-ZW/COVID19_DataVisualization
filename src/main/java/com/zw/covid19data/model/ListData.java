package com.zw.covid19data.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListData implements Serializable {
    @Getter
    private List<String> dates;
    @Getter
    private List<Integer> dailyNewCases;
    @Getter
    private List<Integer> dailyTotalCases;
    @Getter
    private List<Integer> dailyTotalDeaths;
    @Getter
    private List<Integer> dailyTotalRecoveries;
    @Getter
    private List<Integer> dailyTotalActive;

    public ListData(){
        this.dates = new ArrayList<String>();
        this.dailyNewCases = new ArrayList<Integer>();
        this.dailyTotalCases = new ArrayList<Integer>();
        this.dailyTotalDeaths = new ArrayList<Integer>();
        this.dailyTotalRecoveries = new ArrayList<Integer>();
        this.dailyTotalActive = new ArrayList<Integer>();
    }

    public void addDates(String date) {
        this.dates.add(date);
    }

    public void addDailyNewCases(Integer dailyNewCase) {
        this.dailyNewCases.add(dailyNewCase);
    }

    public void addDailyTotalCases(Integer dailyTotalCase) {
        this.dailyTotalCases.add(dailyTotalCase);
    }

    public void addDailyTotalDeaths(Integer dailyTotalDeath) {
        this.dailyTotalDeaths.add(dailyTotalDeath);
    }

    public void addDailyTotalRecoveries(Integer dailyTotalRecovery) {
        this.dailyTotalRecoveries.add(dailyTotalRecovery);
    }

    public void addDailyTotalActive(Integer dailyTotalActive) {
        this.dailyTotalActive.add(dailyTotalActive);
    }

    public void updateListData(DailyCases dailyCases){
        this.addDates(dailyCases.getDate());
        this.addDailyNewCases(dailyCases.getNum_cases());
        // add Daily Total Cases
        if(this.getDailyTotalCases().isEmpty()){
            this.addDailyTotalCases(dailyCases.getNum_cases());
        }else{
            int lastTotal = this.getDailyTotalCases().get(this.getDailyTotalCases().size()-1);
            this.addDailyTotalCases(lastTotal+dailyCases.getNum_cases());
        }

        // add Daily Total Deaths
        if(this.getDailyTotalDeaths().isEmpty()){
            this.addDailyTotalDeaths(dailyCases.getNum_deaths());
        }else{
            int lastTotalDeaths = this.getDailyTotalDeaths().get(this.getDailyTotalDeaths().size()-1);
            this.addDailyTotalDeaths(lastTotalDeaths);
        }

        // add Daily Total Recoveries
        if(this.getDailyTotalRecoveries().isEmpty()){
            this.addDailyTotalRecoveries(dailyCases.getNum_recoveries());
        }else{
            int lastTotalRecoveries = this.getDailyTotalRecoveries().get(this.getDailyTotalRecoveries().size()-1);
            this.addDailyTotalRecoveries(lastTotalRecoveries);
        }

        // add Daily Total Active
        int lastTotal = this.getDailyTotalCases().get(this.getDailyTotalCases().size()-1);
        int totalActive = lastTotal - dailyCases.getNum_deaths() - dailyCases.getNum_recoveries();
        this.addDailyTotalActive(totalActive);
    }

}
