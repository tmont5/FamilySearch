package Handlers;

import Request.PersonRequest;
import Result.LoginResult;
import Result.PersonResult;
import Result.RegisterResult;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {

    Gson gson = new Gson();
    WriteString writeString = new WriteString();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        String authToken = null;
        ///LoginResult result = new LoginResult();
        try{
            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    authToken = exchange.getRequestHeaders().getFirst("Authorization");
                }

                if(exchange.getRequestURI().toString().contains("/person/")){
                    String url = exchange.getRequestURI().toString();
                    String[] urlArray = url.split("/");
                    String personID = urlArray[2];


                    PersonRequest request = new PersonRequest(authToken, personID);
                    PersonService service = new PersonService();
                    PersonResult result = service.getPerson(request);

                    if(!result.isSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    }else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    }
                } else{
                    PersonRequest request = new PersonRequest(authToken, null);
                    PersonService service = new PersonService();
                    PersonResult result = service.getPersons(request);

                    if(!result.isSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();
                        success = true;
                    }else {

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString.writeString(gsonString, respBody);
                        respBody.close();

                        success = true;
                    }
                }
            }
        }catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
