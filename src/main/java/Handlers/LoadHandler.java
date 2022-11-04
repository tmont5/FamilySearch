package Handlers;

import Request.LoadRequest;
import Result.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class LoadHandler implements HttpHandler {


    private Gson gson = new Gson();
    ReadString readString = new ReadString();
    WriteString writeString = new WriteString();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString.readString(reqBody);
                System.out.println(reqData);

                //Load data to database
                LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

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
        }catch (Exception e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
