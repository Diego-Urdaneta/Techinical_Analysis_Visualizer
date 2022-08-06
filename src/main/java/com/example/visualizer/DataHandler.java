package com.example.visualizer;

import java.net.*;
import java.net.http.*;
import java.time.ZonedDateTime;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataHandler{
    String stock;
    String limit;
    String start;
    ZonedDateTime start_time;
    String period;
    HttpClient client;
    int num;
    int val;
    int adder;
    String prd;
    Series series;

    Boolean is_series_og;
    List<String> check_time;

    List<String> blank_time;
    List<String> series_time;
    List<Data> blank_array;
    List<Data> series_array;
    List<Data> adder_array;

    DataHandler(Series series){
        this.series = series;
        stock = "AMZN";
        limit = "100";
        period = "1Min";
        adder = 0;
        val = 0;
        is_series_og = true;

        String[] split = period.split("(?<=\\d)(?=\\D)");
        num = Integer.parseInt(split[0]);
        prd = split[1];

        start_time =  ZonedDateTime.parse("2022-02-07T14:00:00.22Z");

        if(prd.equals("Min")) {start =  (start_time.plusMinutes(-1)).toString();}
        if(prd.equals("Hour")){start =  (start_time.plusHours(-1)).toString()  ;}
        if(prd.equals("Day")) {start =  (start_time.plusDays(-1)).toString()   ;}


    }

    void request(){
        client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://data.alpaca.markets/v2/stocks/"+stock+"/bars?timeframe="+period+"&start="+start+"&limit="+limit))
                .headers("APCA-API-KEY-ID","PKCNIV2DDIVR8NA9DS8L",
                        "APCA-API-SECRET-KEY","rH5xbDQCya7arPUtRvfk9m177PPxASApcuN7fCza")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::map)
                .thenAccept(series::setSeries)
                .join();

    }

    void query(boolean bool){
        long mover = 0;

        if (bool) {
            mover = num;
            val += 1;
        }
        if (!bool){
            mover = -num;
            val -=1;
        }

        ZonedDateTime time = ZonedDateTime.parse(start);


        if(prd.equals("Min")){start = (time.plusMinutes(mover)).toString();}
        if(prd.equals("Hour")){start = (time.plusHours(mover)).toString();}
        if(prd.equals("Day")){start =(time.plusDays(mover)).toString();}

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://data.alpaca.markets/v2/stocks/"+stock+"/bars?timeframe="+period+"&start="+start+"&limit="+limit))
                .headers("APCA-API-KEY-ID","PKCNIV2DDIVR8NA9DS8L",
                        "APCA-API-SECRET-KEY","rH5xbDQCya7arPUtRvfk9m177PPxASApcuN7fCza")
                .build();

        String contents = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();

        JSONArray stock = new JSONObject(contents).getJSONArray("bars");

        JSONObject point = stock.getJSONObject(0);

        String t = point.getString("t");

        double blank_low = 0;

        try {
            blank_low = blank_array.stream().map(Data::getLow).sorted().distinct().toList().get(1);
        }
        catch(Exception e) {
            blank_low = blank_array.stream().map(Data::getLow).sorted().distinct().toList().get(0);
        }



        if(is_series_og){
            check_time = series_time;
            adder_array = series.series;
        }

        if(t.equals(check_time.get(0))) {
            is_series_og = false;
            if (!bool) {
                double series_low = series.series.stream().map(Data::getLow).sorted().distinct().toList().get(0);
                adder += 1;
                if(adder+Integer.parseInt(limit) > adder_array.size()) {
                    if (series_low <= blank_low) {
                        adder_array.add(new Data().setAll(start, series_low, series_low, series_low, series_low, 0));
                    }
                    if (blank_low < series_low) {
                        adder_array.add(new Data().setAll(start, blank_low, blank_low, blank_low, blank_low, 0));
                    }
                }
            }
            if (bool) {
                double series_low = series.series.stream().map(Data::getLow).sorted().distinct().toList().get(0);
                adder -= 1;
                if(adder <= 0) {
                    adder = 0;
                    if (series_low <= blank_low) {
                        adder_array.add(0,new Data().setAll(start, series_low, series_low, series_low, series_low, 0));
                    }
                    if (blank_low < series_low) {
                        adder_array.add(0,new Data().setAll(start, blank_low, blank_low, blank_low, blank_low, 0));
                    }
                }
            }
            series.setSeries(adder_array.subList(adder,adder+Integer.parseInt(limit)));
        }

        if(!t.equals(check_time.get(0))){
            is_series_og = true;
            series.setSeries(map(contents));
            adder = 0;
        }
    }

    List<Data> map(String contents){
        series_array = new ArrayList<>();
        series_time = new ArrayList<>();
        blank_array = new ArrayList<>();
        blank_time = new ArrayList<>();

        JSONArray stock = new JSONObject(contents).getJSONArray("bars");

        for(int i = stock.length()-1 ; i >= 0; i --){
            Data data = new Data();
            JSONObject point = stock.getJSONObject(i);
            String t = point.getString("t");
            double h = point.getDouble("h");
            double l = point.getDouble("l");
            double c = point.getDouble("c");
            double o = point.getDouble("o");
            int v = point.getInt("v");
            data.setAll(t, h, l, o, c, v);
            series_array.add(data);
            series_time.add(t);
        }

        if(prd.equals("Min")){
            for (int i = 0; i < Integer.parseInt(limit); i++){
                blank_array.add(new Data().setAll(start_time.plusMinutes(i+val).toString().replace(".220",""),0,0,0,0,0));
                blank_time.add(start_time.plusMinutes(i+val).toString().replace(".220",""));}}

        if(prd.equals("Hour")){
            for (int i = 0; i < Integer.parseInt(limit); i++){
                blank_array.add(new Data().setAll(start_time.plusHours(i+val).toString().replace(".220",""),0,0,0,0,0));
                blank_time.add(start_time.plusHours(i+val).toString().replace(".220",""));}}

        if(prd.equals("Day")){
            for (int i = 0; i < Integer.parseInt(limit); i++){
                blank_array.add(new Data().setAll(start_time.plusDays(i+val).toString().replace(".220",""),0,0,0,0,0));
                blank_time.add(start_time.plusDays(i+val).toString().replace(".220",""));}}


        for (int i = 0; i < Integer.parseInt(limit); i++){
            if(series_time.contains(blank_time.get(i))){
                blank_array.set(i,series_array.get(series_time.indexOf(blank_time.get(i))));}}

        double blank_low = blank_array.stream().map(Data::getLow).sorted().distinct().toList().get(1);

        for (int i = 0; i < Integer.parseInt(limit); i++){
            if(blank_array.get(i).getVolume() == 0) {blank_array.set(i, blank_array.get(i).setAll(blank_array.get(i).getTime(),blank_low,blank_low,blank_low,blank_low,0));
            }
        }

        return blank_array;
    }
}
