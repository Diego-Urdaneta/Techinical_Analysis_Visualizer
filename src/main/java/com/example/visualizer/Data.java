package com.example.visualizer;

public class Data{
    String time;
    double high;
    double low;
    double open;
    double close;
    int volume;

    public Data(){}

    public Data setAll(String time, double high, double low, double open, double close, int volume) {
        this.time = time;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        return this;}

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
