package app.handlers;

import com.skillcorp.sejoframework.web.IRequestHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HomeHandler implements IRequestHandler {

    private String message;

    public HomeHandler(String message)
    {
        this.message = message;
    }


    @Override
    public String execute(HttpExchange req)
    {
        return "Hello, Sejo say: "+message;
    }
}
