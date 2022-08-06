package com.example.visualizer;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.reverseOrder;

public class Series {

    List<Data> series = new ArrayList<>();

    List<ZonedDateTime> Days;

    List<ZonedDateTime> Hours;

    public void setSeries(List<Data> dataset){series = dataset;
        Days = new ArrayList<>();
        for (Data data:this.series){
            Days.add(ZonedDateTime.parse(data.getTime()).with(LocalTime.of(0,0)));
        }
        Hours = new ArrayList<>();
        for (Data data:this.series){
            Hours.add(ZonedDateTime.parse(data.getTime()).with(LocalTime.of(
                    ZonedDateTime.parse(data.getTime()).getHour(),0)));
        }
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

    public int getFirstTime(ZonedDateTime DOY){
        List<ZonedDateTime> time = new ArrayList<>();
        for (Data data:this.series){
            time.add(ZonedDateTime.parse(data.getTime()).with(LocalTime.of(0,0)));
        }
        List<ZonedDateTime> sorted_time = time.stream().sorted(Comparator.reverseOrder()).toList();
        return sorted_time.indexOf(DOY);
    }

    public int getLastTime(ZonedDateTime DOY){
        List<ZonedDateTime> time = new ArrayList<>();
        for (Data data:this.series){
            time.add(ZonedDateTime.parse(data.getTime()).with(LocalTime.of(0,0)));}
        List<ZonedDateTime> sorted_time = time.stream().sorted().toList();
        return sorted_time.size()-sorted_time.indexOf(DOY)-1;
    }

    public int getFirstTimeHour(ZonedDateTime HAD){
        List<ZonedDateTime> time = new ArrayList<>();
        for (Data data:this.series){
            time.add(ZonedDateTime.parse(data.getTime()).with(LocalTime.of(
                    ZonedDateTime.parse(data.getTime()).getHour(),0)));
        }
        List<ZonedDateTime> sorted_time = time.stream().sorted(Comparator.reverseOrder()).toList();
        return sorted_time.indexOf(HAD);
    }

    public int getLastTimeHour(ZonedDateTime HAD){
        List<ZonedDateTime> time = new ArrayList<>();
        for (Data data:this.series){
            time.add(ZonedDateTime.parse(data.getTime()).with(LocalTime.of(
                    ZonedDateTime.parse(data.getTime()).getHour(),0)));}
        List<ZonedDateTime> sorted_time = time.stream().sorted().toList();
        return sorted_time.size()-sorted_time.indexOf(HAD)-1;
    }
}
