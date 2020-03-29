package com.zw.covid19data.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DailyCases {
    private String date;
    private int num_cases;
    private int num_deaths;
    private int num_recoveries;
    private int total_cases;

    public DailyCases(){
        this.date = null;
        this.num_cases = 0;
        this.num_deaths = 0;
        this.num_recoveries = 0;
        this.total_cases = 0;
    }

    public void setTotal(int previousTotal){
        this.total_cases = previousTotal+this.num_cases;
    }

    @Override
    public String toString(){
        return "{\"date\":" + this.date +
                ",\"num_cases\":"+ this.num_cases +
                ",\"num_deaths\":"+ this.num_deaths +
                ",\"num_recoveries\":"+ this.num_recoveries +
                ",\"total_cases\":" + this.total_cases +
                "}";
    }
}
