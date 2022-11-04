package Handlers;

import Request.EventRequest;
import Result.EventResult;
import Services.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class EventHandler implements HttpHandler {

    Gson gson = new Gson();
    WriteString writeString = new WriteString();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        String authToken = null;

        try{
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    authToken = exchange.getRequestHeaders().getFirst("Authorization");
                }

                if (exchange.getRequestURI().toString().contains("/event/")) {
                    String url = exchange.getRequestURI().toString();
                    String[] urlArray = url.split("/");
                    String eventID = urlArray[2];

                    EventRequest request = new EventRequest(authToken, eventID);
                    EventService service = new EventService();
                    EventResult result = service.getEvent(request);

                    if (!result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    }
                } else {
                    EventRequest request = new EventRequest(authToken, null);
                    EventService service = new EventService();
                    EventResult result = service.getEvents(request);

                    if (!result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    }
                }
            }
        }catch (Exception e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
