package app.http.handlers;

import com.skillcorp.sejoframework.web.IRequestHandler;
import com.sun.net.httpserver.HttpExchange;

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
