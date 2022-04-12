package com.example.visualizer;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Axes extends Parent{
    double YIter;
    int XIter;
    Line YAxis = new Line(1100,15,1100,492);
    Line VYAxis = new Line(1100,492,1100,615);
    Line XAxis = new Line(0,492,1100,492);
    Line VXAxis = new Line(0,615,1100,615);
    Series series;

    public Axes(Candlestick_Graph graph){
    YAxis.setStrokeWidth(1.25);
    YAxis.setStroke(Color.WHITE);
    XAxis.setStrokeWidth(1.25);
    XAxis.setStroke(Color.WHITE);
    VXAxis.setStrokeWidth(1.25);
    VXAxis.setStroke(Color.WHITE);
    VYAxis.setStrokeWidth(1.25);
    VYAxis.setStroke(Color.WHITE);
    series = graph.getSeries();
    YIter = series.getHigh()-series.getLow();
    XIter = series.getSeries().size()+1;
    Ticks ticks = new Ticks(this);
    this.getChildren().add(YAxis);
    this.getChildren().add(XAxis);
    this.getChildren().add(VXAxis);
    this.getChildren().add(VYAxis);
    this.getChildren().add(ticks.tick());

        for (Data data: series.getSeries()) {
            this.getChildren().add(new Candle(series,this,data));
            this.getChildren().add(new VolumeBars(series,this,data));
        }

    }

    public int getXIter(){
        return XIter;
    }

    public double getYIter(){
        return YIter;
    }

    public Line getXAxis(){
        return XAxis;
    }

    public Line getYAxis(){
        return YAxis;
    }

    public Line getVXAxis(){
        return VXAxis;
    }

    public Line getVYAxis(){
        return VYAxis;
    }

    public Series getSeries(){
        return series;
    }
    }


