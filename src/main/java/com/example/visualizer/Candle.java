package com.example.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Collections;
import java.util.List;

public class Candle extends Group{
    double riser;
    Line YAxis;
    Line XAxis;
    double XIter;
    Series series;
    List<Data> rev_series;

    public Candle(Series series, Axes axes) {
        this.series = series;
        riser = axes.getMover();
        XAxis = axes.getXAxis();
        YAxis = axes.getYAxis();
        XIter = series.series.size()+1;

    }

    void clear(){
        this.getChildren().clear();
    }

    void create(){
        rev_series = series.series;
        Collections.reverse(rev_series);
        double YOffset = YAxis.getEndY() - 10;
        double YRatio = YAxis.getEndY() - YAxis.getStartY();
        double XRatio = XAxis.getEndX() - XAxis.getStartX();
        double SRatio = series.getHigh() - series.getLow();

        for (Data data : rev_series) {
            System.out.println(data.getVolume() +"  "+ data.getTime());
            Rectangle bear = new Rectangle();
            Rectangle bull = new Rectangle();
            Rectangle wick = new Rectangle();

            bull.setWidth(YRatio / series.series.size());
            bull.setStroke(Color.WHITE);
            bull.setFill(Color.GREEN);
            bull.setStrokeWidth(bull.getWidth() * 0.1);
            bull.setHeight(YOffset -
                    ((YOffset - YAxis.getStartY()) * (data.getOpen() - series.getLow()) / SRatio) -
                    (YOffset - ((data.getClose() - series.getLow()) / SRatio) * (YOffset - YAxis.getStartY())) + riser);
            bull.relocate(XAxis.getEndX() - (XRatio * (series.series.indexOf(data) + 1) / XIter) - bull.getWidth() / 2 - bull.getStrokeWidth() / 2,
                    (YOffset - ((data.getClose() - series.getLow()) / SRatio) * (YOffset - YAxis.getStartY())) + riser);

            bear.setWidth(YRatio / series.series.size());
            bear.setStroke(Color.WHITE);
            bear.setStrokeWidth(bear.getWidth() * 0.1);
            bear.setFill(Color.RED);
            bear.setHeight(YOffset -
                    ((YOffset - YAxis.getStartY()) * (data.getClose() - series.getLow()) / SRatio) -
                    (YOffset - ((data.getOpen() - series.getLow()) / SRatio) * (YOffset - YAxis.getStartY())) + riser);
            bear.relocate(XAxis.getEndX() - (XRatio * (series.series.indexOf(data) + 1) / XIter) - bear.getWidth() / 2 - bear.getStrokeWidth() / 2,
                    (YOffset - ((data.getOpen() - series.getLow()) / SRatio) * (YOffset - YAxis.getStartY())) + riser);

            wick.setWidth(bear.getWidth() * 0.1);
            wick.setFill(Color.WHITE);
            wick.setHeight(YOffset -
                    ((YOffset - YAxis.getStartY()) * (data.getLow() - series.getLow()) / SRatio) -
                    (YOffset - ((data.getHigh() - series.getLow()) / SRatio) * (YOffset - YAxis.getStartY())) + riser);
            wick.relocate(XAxis.getEndX() - (XRatio * (series.series.indexOf(data) + 1) / XIter) - wick.getWidth() / 2,
                    (YOffset - ((data.getHigh() - series.getLow()) / SRatio) * (YOffset - YAxis.getStartY())) + riser);

            this.getChildren().add(wick);
            this.getChildren().add(bear);
            this.getChildren().add(bull);
        }
    }
}
