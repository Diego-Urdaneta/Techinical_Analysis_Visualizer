package com.example.visualizer;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Axes extends Parent{
    double mover;
    Line YAxis = new Line(1100,15,1100,492);
    Line VYAxis = new Line(1100,492,1100,615);
    Line XAxis = new Line(0,492,1100,492);
    Line VXAxis = new Line(0,615,1100,615);

    public Axes(Candlestick_Graph graph){
        YAxis.setStrokeWidth(1.25);
        YAxis.setStroke(Color.WHITE);
        XAxis.setStrokeWidth(1.25);
        XAxis.setStroke(Color.WHITE);
        VXAxis.setStrokeWidth(1.25);
        VXAxis.setStroke(Color.WHITE);
        VYAxis.setStrokeWidth(1.25);
        VYAxis.setStroke(Color.WHITE);

        mover = 0;

        this.getChildren().add(YAxis);
        this.getChildren().add(XAxis);
        this.getChildren().add(VXAxis);
        this.getChildren().add(VYAxis);
    }

    public double getMover(){
        return mover;
    }

    public void setMover(double val) {
        mover = val;
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
}
