package com.example.visualizer;

import java.util.ArrayList;

public class Data{
    String time;
    double high;
    double low;
    double open;
    double close;
    int volume;
    ArrayList<Data> series;

    public Data(){}

    public void setAll(String time, double high, double low, double open, double close, int volume) {
        this.time = time;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;}

    public String getTime() {
        return time;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {return close;}

    public int getVolume() {return volume;}
}
