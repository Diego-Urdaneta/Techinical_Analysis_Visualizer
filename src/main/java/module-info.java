module com.example.visualizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;


    opens com.example.visualizer to javafx.fxml;
    exports com.example.visualizer;
}