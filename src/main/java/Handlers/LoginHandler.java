package Handlers;


import Request.LoginRequest;
import Result.LoadResult;
import Result.LoginResult;
import Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    Gson gson = new Gson();
    WriteString writeString = new WriteString();
    ReadString readString = new ReadString();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();//get the request body
                String reqData = readString.readString(reqBody);
                System.out.println(reqData);//read the request body

                LoginRequest request = (LoginRequest) gson.fromJson(reqData, LoginRequest.class);
                LoginService service = new LoginService();
                LoginResult result = service.login(request);

                if (result.getAuthToken() == null) {
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
        }catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}
