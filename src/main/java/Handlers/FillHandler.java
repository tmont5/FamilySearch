package Handlers;

import DataAccess.UserDao;
import Request.FillRequest;
import Request.LoadRequest;
import Result.FillResult;
import Services.FillServices;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {

    Gson gson = new Gson();
    WriteString writeString = new WriteString();
    ReadString readString = new ReadString();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {

                if (exchange.getRequestURI().toString().contains("/fill/")) {
                    String url = exchange.getRequestURI().toString();
                    String[] urlArray = url.split("/");
                    String username = urlArray[2];
                    if(exchange.getRequestURI().toString().contains("/fill/" + username + "/")){
                        String generations = urlArray[3];
                        FillRequest request = new FillRequest(username, Integer.parseInt(generations));
                        FillServices service = new FillServices();
                        FillResult result = service.fillGenerations(request);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();

                        success = true;
                    }else {
                        FillRequest request = new FillRequest(username);
                        FillServices service = new FillServices();
                        FillResult result = service.fill(request);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();

                        success = true;
                    }
                }
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }catch(Exception e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
