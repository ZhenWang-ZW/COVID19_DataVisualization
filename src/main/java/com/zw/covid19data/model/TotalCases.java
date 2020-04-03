package com.zw.covid19data.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;


public class TotalCases implements Serializable {
    private static TotalCases single_instance = null;

    public static TotalCases getInstance()
    {
        if (single_instance == null)
            single_instance = new TotalCases();
        return single_instance;
    }


    @Getter
    private int totalInfected;
    @Getter
    private int totalDeaths;
    @Getter
    private int totalRecoveries;
    @Getter
    private int restCases;

    public void setTotalInfect(List<DailyCases> dataset) {
        this.totalInfected = 0;
        for (DailyCases dailyCase:dataset){
            this.totalInfected +=dailyCase.getNum_cases();
        }
    }

    public void setTotalDeaths(List<DailyCases> dataset){
        this.totalDeaths = 0;
        for (DailyCases dailyCase:dataset){
            this.totalDeaths +=dailyCase.getNum_deaths();
        }
    }

    public void setTotalRecoveries(List<DailyCases> dataset){
        this.totalRecoveries = 0;
        for (DailyCases dailyCase:dataset){
            this.totalRecoveries +=dailyCase.getNum_recoveries();
        }
    }

    public void setRestCases() {
        this.restCases = this.totalInfected - this.totalDeaths - this.totalRecoveries;
    }
}
