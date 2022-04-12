package com.example.visualizer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataMapper{

    public ArrayList<Data> Map_Data(String contents){
        ArrayList<Data> data_array = new ArrayList<>();

        JSONArray stock = new JSONObject(contents).getJSONArray("bars");

        for(int i = stock.length()-1; i >= 0;i --){

            Data data = new Data();

            JSONObject point = stock.getJSONObject(i);
            String t = point.getString("t");
            double h = point.getDouble("h");
            double l = point.getDouble("l");
            double c = point.getDouble("c");
            double o = point.getDouble("o");
            int v = point.getInt("v");
            data.setAll(t, h, l, o, c, v);
            data_array.add(data);
        }
        return data_array;
    }

}