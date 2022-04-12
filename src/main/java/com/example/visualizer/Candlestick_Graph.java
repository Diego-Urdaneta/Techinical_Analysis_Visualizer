package com.example.visualizer;

import javafx.scene.layout.Pane;

public class Candlestick_Graph extends Pane {

    Series series;
    public Candlestick_Graph(Series series) {
        this.series = series;
        this.setStyle("-fx-background-color: black;");
        this.setPrefSize(1200, 700);
        this.getChildren().add(new Axes(this));
        }

    public Series getSeries(){return series;}
    }
