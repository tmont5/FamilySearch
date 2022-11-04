package Handlers;

import DataAccess.DataAccessException;
import Request.ClearRequest;
import Result.ClearResult;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;


public class ClearHandler implements HttpHandler {


    public ClearHandler() {
    }

    private Gson gson = new Gson();
    WriteString writeString = new WriteString();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                ClearRequest request = new ClearRequest();
                ClearService service = new ClearService();
                ClearResult result = service.clear(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                String gsonString = gson.toJson(result);
                writeString.writeString(gsonString, respBody);
                respBody.close();

                success = true;
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
