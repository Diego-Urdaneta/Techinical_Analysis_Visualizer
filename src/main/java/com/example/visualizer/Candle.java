package com.example.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Candle extends Group{
    Line YAxis;
    Line XAxis;
    double YIter;
    double XIter;

    Rectangle bear = new Rectangle();
    Rectangle bull = new Rectangle();

    Rectangle wick = new Rectangle();

    public Candle(Series series, Axes axes, Data data){
        XAxis = axes.getXAxis();
        YAxis = axes.getYAxis();
        YIter = axes.getYIter();
        XIter = axes.getXIter();
        double YOffset = YAxis.getEndY()-10;
        double YRatio = YAxis.getEndY()-YAxis.getStartY();
        double XRatio = XAxis.getEndX()-XAxis.getStartX();
        double SRatio = series.getHigh()-series.getLow();

        bull.setWidth(YRatio/series.getSeries().size());
        bull.setStroke(Color.WHITE);
        bull.setFill(Color.GREEN);
        bull.setStrokeWidth(bull.getWidth()*0.1);
        bull.setHeight(YOffset-
                ((YOffset- YAxis.getStartY())*(data.getOpen()-series.getLow())/SRatio) -
                (YOffset-((data.getClose()-series.getLow())/SRatio)*(YOffset- YAxis.getStartY())));
        bull.relocate(XAxis.getEndX() - (XRatio*(series.getSeries().indexOf(data)+1)/XIter) - bull.getWidth()/2 - bull.getStrokeWidth()/2,
                (YOffset-((data.getClose()-series.getLow())/SRatio)* (YOffset- YAxis.getStartY())));

        bear.setWidth(YRatio/series.getSeries().size());
        bear.setStroke(Color.WHITE);
        bear.setStrokeWidth(bear.getWidth()*0.1);
        bear.setFill(Color.RED);
        bear.setHeight(YOffset-
                ((YOffset- YAxis.getStartY())*(data.getClose()-series.getLow())/SRatio) -
                (YOffset-((data.getOpen()-series.getLow())/SRatio)*(YOffset - YAxis.getStartY())));
        bear.relocate(XAxis.getEndX() - (XRatio*(series.getSeries().indexOf(data)+1)/XIter) - bear.getWidth()/2 - bear.getStrokeWidth()/2,
                (YOffset-((data.getOpen()-series.getLow())/SRatio)* (YOffset - YAxis.getStartY())));

        wick.setWidth(bear.getWidth()*0.1);
        wick.setFill(Color.WHITE);
        wick.setHeight(YOffset-
                ((YOffset-YAxis.getStartY())*(data.getLow()-series.getLow())/SRatio) -
                (YOffset-((data.getHigh()-series.getLow())/SRatio)*(YOffset-YAxis.getStartY())));
        wick.relocate(XAxis.getEndX() - (XRatio*(series.getSeries().indexOf(data)+1)/XIter) - wick.getWidth()/2,
                (YOffset-((data.getHigh()-series.getLow())/SRatio)*(YOffset- YAxis.getStartY())));

        this.getChildren().add(wick);
        this.getChildren().add(bear);
        this.getChildren().add(bull);
    }
}

