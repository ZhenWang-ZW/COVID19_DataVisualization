package com.zw.covid19data.model;

import lombok.Getter;
import lombok.Setter;

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
    private int totalInfecte;
    @Getter
    private int totalDeaths;
    @Getter
    private int totalRecovires;
    @Getter
    private int restCases;

    public void setTotalInfecte(List<DailyCases> dataset) {
        this.totalInfecte = 0;
        for (DailyCases dailyCase:dataset){
            this.totalInfecte+=dailyCase.getNum_cases();
        }
    }

    public void setTotalDeaths(List<DailyCases> dataset){
        this.totalRecovires = 0;
        for (DailyCases dailyCase:dataset){
            this.totalRecovires+=dailyCase.getNum_deaths();
        }
    }

    public void setTotalRecovires(List<DailyCases> dataset){
        this.totalDeaths = 0;
        for (DailyCases dailyCase:dataset){
            this.totalDeaths+=dailyCase.getNum_recoveries();
        }
    }

    public void setRestCases() {
        this.restCases = this.totalInfecte - this.totalDeaths - this.totalRecovires;
    }
}
