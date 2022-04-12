package com.example.visualizer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class VolumeBars extends Group {
    Rectangle bar = new Rectangle();
    Line YAxis;
    Line XAxis;
    Line VYAxis;
    double XIter;

    public VolumeBars(Series series, Axes axes, Data data){
        XAxis = axes.getXAxis();
        YAxis = axes.getYAxis();
        VYAxis = axes.getVYAxis();
        XIter = axes.getXIter();

        double YRatio = YAxis.getEndY()-YAxis.getStartY();
        double XRatio = XAxis.getEndX()-XAxis.getStartX();

        bar.setWidth(YRatio/series.getSeries().size());
        bar.setStroke(Color.WHITE);
        bar.setFill(Color.DEEPSKYBLUE);
        bar.setStrokeWidth(bar.getWidth()*0.1);
        bar.relocate(
                XAxis.getEndX() - (XRatio*(series.getSeries().indexOf(data)+1)/XIter) - bar.getWidth()/2 - bar.getStrokeWidth()/2,
                (VYAxis.getEndY()-10-((data.getVolume()/
                        (series.getVHigh()))*(VYAxis.getEndY()-20-VYAxis.getStartY()))));
        bar.setHeight(((data.getVolume()/
                (series.getVHigh()))*(VYAxis.getEndY()-20-VYAxis.getStartY())));

        this.getChildren().add(bar);
    }

}
