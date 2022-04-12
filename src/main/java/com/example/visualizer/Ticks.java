package com.example.visualizer;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Ticks{
    Axes axes;
    double YIter;
    int XIter;
    Line YAxis;
    Line XAxis;
    Line VXAxis;
    Line VYAxis;
    Series series;
    double YRatio;
    double XRatio;
    Group Ticks;

    public Ticks(Axes axes) {
        this.axes = axes;
        YIter = axes.getYIter();
        XIter = axes.getXIter();
        YAxis = axes.getYAxis();
        XAxis = axes.getXAxis();
        VXAxis = axes.getVXAxis();
        VYAxis = axes.getVYAxis();
        series = axes.getSeries();

        YRatio = YAxis.getEndY()-YAxis.getStartY();
        XRatio = XAxis.getEndX()-XAxis.getStartX();
        Ticks = new Group();
        }

    public void setYTicks(Label label, Line line) {
        Group Tick = new Group();
        double x = line.getEndX() + label.getFont().getSize() * (1 / 3d);
        double y = line.getEndY() - label.getFont().getSize() * (2 / 3d);
        line.setStrokeWidth(1.25);
        line.setStroke(Color.WHITE);
        label.relocate(x,y);
        label.setTextFill(Color.WHITE);
        Tick.getChildren().add(line);
        Tick.getChildren().add(label);
        Ticks.getChildren().add(Tick);
    }

    public void setXTicks(Label label, Line line) {
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
        Ticks.getChildren().add(Tick);
    }

    public Group tick() {
        // Tick for Y-Axis
        for (double i = 0; i <= 25; i++){
            setYTicks(
            new Label(Double.toString(Math.round((((series.getHigh()- series.getLow())*i/25)+ series.getLow())*100)/100d)),
            new Line(YAxis.getStartX(),YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25)),
                    YAxis.getStartX()+5,YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25))));
            if(i%5==0){
                setLongLine(YAxis.getStartX(),YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25)),
                        XAxis.getStartX(),YAxis.getEndY()-10-((YAxis.getEndY()- 10-YAxis.getStartY())*(i)/(25)));
            }
        }

        // Tick for VY-Axis
        for (double i = 0; i <= 4; i++){
            setYTicks(
                    new Label(Double.toString(Math.round((((series.getVHigh()- series.getVLow())*i/4)+ series.getVLow())*100)/100d)),
                    new Line(YAxis.getStartX(),VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4)),
                            YAxis.getStartX()+5,VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4))));
                setLongLine(YAxis.getStartX(),VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4)),
                        XAxis.getStartX(),VYAxis.getEndY()-10-((VYAxis.getEndY()-20- VYAxis.getStartY())*(i)/(4)));
        }

        // Tick for X-Axis
        for (int i = XIter-2; i >= 0; i--){
            if(i%10==0) {
                setXTicks(
                        new Label(
                                convertTime(series.getSeries().get(i).getTime())),
                        new Line(XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                VXAxis.getStartY(), XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                VXAxis.getStartY() + 5));
                        setLongLine(XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                VXAxis.getStartY(), XAxis.getEndX() - (XRatio * (i + 1) / XIter),
                                YAxis.getStartY());
            }
            }
        return Ticks;
}
    public String convertTime(String ISODate){
        Instant instant = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(ISODate, Instant::from);
        Date myDate = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(myDate);
    }

    public void setLongLine(double SX, double SY, double EX, double EY){
        Line line = new Line(SX, SY, EX, EY);
        line.setOpacity(0.5);
        line.setStrokeWidth(0.75);
        line.setStroke(Color.DARKGRAY);
        Ticks.getChildren().add(line);
    }
}