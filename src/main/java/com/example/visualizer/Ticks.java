package com.example.visualizer;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ticks{
    Axes axes;
    int XIter;
    Line YAxis;
    Line XAxis;
    Line VXAxis;
    Line VYAxis;
    Series series;
    double YRatio;
    double XRatio;
    ArrayList<Label> YList;
    int mover;
    String prd;
    Group XTicks1;
    Group XTicks2;
    Group YTicks;
    Group VYTicks;
    Group TimeLine;

    public Ticks(Axes axes, Series series, DataHandler dataHandler) {
        this.axes = axes;
        this.series = series;
        XIter = series.series.size()+1;
        YAxis = axes.getYAxis();
        XAxis = axes.getXAxis();
        VXAxis = axes.getVXAxis();
        VYAxis = axes.getVYAxis();

        YRatio = YAxis.getEndY()-YAxis.getStartY();
        XRatio = XAxis.getEndX()-XAxis.getStartX();

        YList = new ArrayList<>();
        mover = 0;

        XTicks1 = new Group();
        XTicks2 = new Group();
        YTicks = new Group();
        VYTicks = new Group();

        TimeLine = new Group();
        prd = dataHandler.prd;
    }

    public Group setYTicks(Label label, Line line) {
        Group Tick = new Group();
        double x = line.getEndX() + label.getFont().getSize() * (1 / 3d);
        double y = line.getEndY() - label.getFont().getSize() * (2 / 3d);
        line.setStrokeWidth(1.25);
        line.setStroke(Color.WHITE);
        label.relocate(x,y);
        label.setTextFill(Color.WHITE);
        Tick.getChildren().add(line);
        Tick.getChildren().add(label);
        return Tick;
    }

    public Group setXTicks(Label label, Line line) {
        Group Tick = new Group();
        Text text = new Text(label.getText());
        double x = line.getEndX() - text.getBoundsInLocal().getWidth()/2;
        double y = line.getEndY() + label.getFont().getSize() * (1 / 3d);
        line.setStrokeWidth(1.25);
        line.setStroke(Color.WHITE);
        label.setTextFill(Color.WHITE);
        label.relocate(x,y);
        label.setTextAlignment(TextAlignment.CENTER);
        Tick.getChildren().add(line);
        Tick.getChildren().add(label);
        return Tick;
    }

    public String convertTime(String ISODate) {
        ZonedDateTime ZDT = ZonedDateTime.parse(ISODate);
        String format;
        if (prd.equals("Hour")) {
            format =  DateTimeFormatter.ofPattern("hh:mm a").format(ZDT);
            return format;
        }
        if (prd.equals("Min")) {
            format =  DateTimeFormatter.ofPattern("mm:ss").format(ZDT);
            return format;
        }
        if (prd.equals("Day")) {
            format =  DateTimeFormatter.ofPattern("d-M/L-u").format(ZDT);
            return format;
        }
        throw new RuntimeException("Non-standard timeframe detected");
    }


    public Line setLongLine(double SX, double SY, double EX, double EY){
        Line line = new Line(SX, SY, EX, EY);
        line.setOpacity(0.5);
        line.setStrokeWidth(0.75);
        line.setStroke(Color.DARKGRAY);
        return line;
    }

    public Label LabelCarousel(ArrayList<Label> list, Label label){
        list.add(label);
        return label;
    }

    public ArrayList<Label> getYList(){return YList;}

    public void setMover(int val) {
        mover = val;
    }

    public int getMover(){return mover;}

    void createXTicks1(){
        for (int i = XIter-2; i >= 0; i--){
            if(i%10==0) {
                XTicks1.getChildren().add(
                        setXTicks(
                                new Label(
                                        convertTime(series.series.get(i).getTime())),
                                new Line(XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                        VXAxis.getStartY(), XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                        VXAxis.getStartY() + 5)));
                XTicks1.getChildren().add(
                        setLongLine(XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                VXAxis.getStartY(), XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                482.0));
                XTicks2.getChildren().add(
                        setLongLine(XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                482.0, XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                YAxis.getStartY()));
            }
        }
    }

    void clearXTicks1() {XTicks1.getChildren().clear();}

    public void createXTicks2(){
        for (int i = XIter-2; i >= 0; i--){
            if(i%10==0) {
                XTicks2.getChildren().add(
                        setLongLine(XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                482.0, XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                YAxis.getStartY()));
            }
        }
    }

    void clearXTicks2() {XTicks2.getChildren().clear();}

    void createYTicks(){
        for (double i = 0; i <= 25; i++){
            YTicks.getChildren().add(
                    setYTicks(
                            LabelCarousel(YList,new Label(Double.toString(Math.round((((series.getHigh()- series.getLow())*i/25)+ series.getLow())*100)/100d))),
                            new Line(YAxis.getStartX(),YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25)),
                                    YAxis.getStartX()+5,YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25)))));
            if(i%5==0){
                YTicks.getChildren().add(
                        setLongLine(YAxis.getStartX(),YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25)),
                                XAxis.getStartX(),YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25))));
            }
        }
    }

    void clearYTicks() {YTicks.getChildren().clear();}

    void createVYTicks(){
        for (double i = 0; i <= 4; i++){
            VYTicks.getChildren().add(
                    setYTicks(new Label(Double.toString(Math.round((((series.getVHigh()- series.getVLow())*i/4)+ series.getVLow())*100)/100d)),
                            new Line(YAxis.getStartX(),VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4)),
                                    YAxis.getStartX()+5,VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4)))));
            VYTicks.getChildren().add(
                    setLongLine(YAxis.getStartX(),VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4)),
                            XAxis.getStartX(),VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4))));
        }
    }

    void clearVYTicks() {VYTicks.getChildren().clear();}

    void clearYList() {YList.clear();}

    void createTimeline(){
        if (prd.equals("Hour")) {
            series.Days = series.Days.stream().sorted().toList();
            for (int i = 0; i < series.Days.stream().distinct().count(); i++) {
                Label label = new Label(DateTimeFormatter.ofPattern("MMMM d").format(series.Days.stream().distinct().toList().get(i)));
                double start = XAxis.getEndX() - XRatio * (series.getFirstTime(series.Days.stream().distinct().toList().get(i)) + 1) / XIter;
                double end = XAxis.getEndX() - XRatio * (series.getLastTime(series.Days.stream().distinct().toList().get(i)) + 1) / XIter;
                Line line = new Line(start, VXAxis.getStartY() + 35, end, VXAxis.getStartY() + 35);
                Text text = new Text(label.getText());
                double x = (start+end)/2-text.getBoundsInLocal().getWidth()/2;
                double y = VXAxis.getStartY() + 45;
                line.setStrokeWidth(1.25);
                line.setStroke(Color.WHITE);
                label.setTextFill(Color.WHITE);
                label.relocate(x,y);
                TimeLine.getChildren().add(label);
                TimeLine.getChildren().add(line);
            }
        }
        if (prd.equals("Min")) {
            series.Hours = series.Hours.stream().sorted().toList();
            for (int i = 0; i < series.Hours.stream().distinct().count(); i++) {
                Label label = new Label(DateTimeFormatter.ofPattern("hh a").format(series.Hours.stream().distinct().toList().get(i)));
                double start = XAxis.getEndX() - XRatio * (series.getFirstTimeHour(series.Hours.stream().distinct().toList().get(i)) + 1) / XIter;
                double end = XAxis.getEndX() - XRatio * (series.getLastTimeHour(series.Hours.stream().distinct().toList().get(i)) + 1) / XIter;
                Line line = new Line(start, VXAxis.getStartY() + 35, end, VXAxis.getStartY() + 35);
                Text text = new Text(label.getText());
                double x = (start+end)/2-text.getBoundsInLocal().getWidth()/2;
                double y = VXAxis.getStartY() + 45;
                line.setStrokeWidth(1.25);
                line.setStroke(Color.WHITE);
                label.setTextFill(Color.WHITE);
                label.relocate(x,y);
                Label label2 = new Label(DateTimeFormatter.ofPattern("MMMM d").
                        format(series.Days.get(series.Days.size()-1)));
                label2.relocate(1115,660);
                label2.setTextFill(Color.WHITE);
                TimeLine.getChildren().add(label2);
                TimeLine.getChildren().add(label);
                TimeLine.getChildren().add(line);
            }
        }
    }

    void clearTimeline(){TimeLine.getChildren().clear();}
}
