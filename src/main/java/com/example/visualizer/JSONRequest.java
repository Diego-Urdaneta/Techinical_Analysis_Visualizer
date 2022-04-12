package com.example.visualizer;

import java.net.*;
import java.net.http.*;

public class JSONRequest {
    Series series;
    public JSONRequest(Series series){
        this.series = series;
    }

    void Request(){
        Data data = new Data();
        DataMapper map = new DataMapper();

        String stock_name = "AAPL";

        String limit = "100";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create("https://data.alpaca.markets/v2/stocks/"+stock_name+"/bars?timeframe=1Min&start=2021-02-01T16:01:00Z&limit="+limit))
                .headers("APCA-API-KEY-ID","PKCNIV2DDIVR8NA9DS8L",
                        "APCA-API-SECRET-KEY","rH5xbDQCya7arPUtRvfk9m177PPxASApcuN7fCza")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(map::Map_Data)
                .thenAccept(series::setSeries)
                .join();
    }
}