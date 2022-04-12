package com.example.visualizer;

import java.util.ArrayList;

import static java.util.Comparator.reverseOrder;

public class Series {ArrayList<Data> series = new ArrayList<>();

    public ArrayList<Data> getSeries(){
        return series;
    }

    public void setSeries(ArrayList<Data> data){
    series.addAll(data);
    }

    public double getHigh(){
        return series.stream().map(Data::getHigh).sorted(reverseOrder()).toList().get(0);
    }

    public double getLow(){
        return series.stream().map(Data::getLow).sorted().toList().get(0);
    }

    public double getVHigh(){
        return series.stream().map(Data::getVolume).sorted(reverseOrder()).toList().get(0);
    }

    public double getVLow(){
        return series.stream().map(Data::getVolume).sorted().toList().get(0);
    }
}