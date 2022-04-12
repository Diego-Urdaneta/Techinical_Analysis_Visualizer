package com.example.visualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();}

    @Override
    public void start(Stage stage) throws IOException {
        Series series = new Series();

        JSONRequest request = new JSONRequest(series);

        request.Request();

        Candlestick_Graph grid = new Candlestick_Graph(series);

        Scene scene = new Scene(grid);

        stage.setScene(scene);

        stage.show();
    }
}