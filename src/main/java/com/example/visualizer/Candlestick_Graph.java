package com.example.visualizer;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Candlestick_Graph extends Pane {
    Series series;
    Axes axes;
    DataHandler dataHandler;

    public Candlestick_Graph(Series series, DataHandler dataHandler) {
        this.series = series;
        this.dataHandler = dataHandler;

        axes = new Axes(this);

        double MoveBy = ((axes.YAxis.getEndY()- 10 -axes.YAxis.getStartY())*(1)/(25));

        Ticks ticks = new Ticks(axes, series, dataHandler);
        Candle candle = new Candle(series,axes);
        VolumeBars vol = new VolumeBars(series,axes);

        Rectangle UpperBorder = new Rectangle(0,0,1100,14);
        Rectangle LowerBorder = new Rectangle(0,482.5,1100,718);
        UpperBorder.setFill(Color.BLACK);
        LowerBorder.setFill(Color.BLACK);


        this.setStyle("-fx-background-color: black;");
        this.setPrefSize(1200, 700);
        ticks.createYTicks();
        this.getChildren().add(ticks.YTicks);
        ticks.createXTicks2();
        this.getChildren().add(ticks.XTicks2);
        candle.create();
        this.getChildren().add(candle);
        this.getChildren().add(LowerBorder);
        this.getChildren().add(UpperBorder);
        this.getChildren().add(axes);
        ticks.createXTicks1();
        this.getChildren().add(ticks.XTicks1);
        vol.create();
        this.getChildren().add(vol);
        ticks.createVYTicks();
        this.getChildren().add(ticks.VYTicks);
        System.out.println("------------");
        ticks.createTimeline();
        this.getChildren().add(ticks.TimeLine);
        this.setOnMouseClicked(event -> this.requestFocus());

        this.setOnKeyPressed(
                event -> {
                    if (event.getCode().getName().equals("Down")) {
                        axes.setMover(axes.getMover()+MoveBy);
                        candle.setTranslateY(axes.getMover());
                        ticks.clearYTicks();
                        ticks.clearYList();
                        ticks.createYTicks();
                        ticks.setMover(ticks.getMover()+1);
                        for (Label label : ticks.getYList()){
                            label.setText(
                                    Double.toString(
                                            Math.round((((series.getHigh() - series.getLow())*(ticks.getYList().indexOf(label)+ticks.getMover())/25)+ series.getLow())*100)/100d));
                        }
                    }

                    if (event.getCode().getName().equals("Up")) {
                        axes.setMover(axes.getMover()-MoveBy);
                        candle.setTranslateY(axes.getMover());
                        ticks.clearYTicks();
                        ticks.clearYList();
                        ticks.createYTicks();
                        ticks.setMover(ticks.getMover()-1);
                        for (Label label : ticks.getYList()){
                            label.setText(
                                    Double.toString(
                                            Math.round((((series.getHigh() - series.getLow())*(ticks.getYList().indexOf(label)+ticks.getMover())/25)+ series.getLow())*100)/100d));
                        }
                    }

                    if (event.getCode().getName().equals("Left")) {
                        dataHandler.query(false);
                        candle.clear();
                        candle.create();
                        vol.clear();
                        vol.create();
                        ticks.clearXTicks1();
                        ticks.clearXTicks2();
                        ticks.clearYTicks();
                        ticks.clearYList();
                        System.out.println("------------");
                        ticks.clearVYTicks();
                        ticks.clearTimeline();
                        ticks.createXTicks1();
                        ticks.createXTicks2();
                        ticks.createYTicks();
                        ticks.createVYTicks();
                        ticks.createXTicks1();
                        ticks.createTimeline();

                        for (Label label : ticks.getYList()){
                            label.setText(
                                    Double.toString(
                                            Math.round((((series.getHigh() - series.getLow())*(ticks.getYList().indexOf(label)+ticks.getMover())/25)+ series.getLow())*100)/100d));
                        }
                    }

                    if (event.getCode().getName().equals("Right")) {
                        dataHandler.query(true);
                        candle.clear();
                        System.out.println("------------");
                        candle.create();
                        vol.clear();
                        vol.create();
                        ticks.clearXTicks1();
                        ticks.clearXTicks2();
                        ticks.clearYTicks();
                        ticks.clearYList();
                        ticks.clearVYTicks();
                        ticks.clearTimeline();
                        ticks.createXTicks1();
                        ticks.createXTicks2();
                        ticks.createYTicks();
                        ticks.createVYTicks();
                        ticks.createTimeline();
                        for (Label label : ticks.getYList()){
                            label.setText(
                                    Double.toString(
                                            Math.round((((series.getHigh() - series.getLow())*(ticks.getYList().indexOf(label)+ticks.getMover())/25)+ series.getLow())*100)/100d));
                        }
                    }
                });
    }

    public Series getSeries(){return series;}
}
