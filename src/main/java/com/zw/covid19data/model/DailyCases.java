package com.zw.covid19data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter @Setter
public class DailyCases implements Serializable,Comparable<DailyCases> {
    private String date;
    private int num_cases;
    private int num_deaths;
    private int num_recoveries;

    public DailyCases(){
        this.date = null;
        this.num_cases = 0;
        this.num_deaths = 0;
        this.num_recoveries = 0;
    }

    @Override
    public String toString(){
        return System.lineSeparator()+
                "{\"date\":" + "\"" +this.date + "\"" +
                ",\"num_cases\":"+ this.num_cases +
                ",\"num_deaths\":"+ this.num_deaths +
                ",\"num_recoveries\":"+ this.num_recoveries +
                "}";
    }

    @SneakyThrows
    @Override
    public int compareTo(DailyCases _case)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(this.getDate()).compareTo(sdf.parse(_case.getDate()));
    }
}
