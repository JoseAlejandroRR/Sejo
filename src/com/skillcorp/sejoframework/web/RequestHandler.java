package com.skillcorp.sejoframework.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class RequestHandler implements HttpHandler {

    private String method;

    private String path;

    private ArrayList<Route> routesMap;

    public ArrayList<String> routes;

    public RequestHandler()
    {
        routesMap = new ArrayList<Route>();
        routes = new ArrayList<String>();
    }

    @Override
    public void handle(HttpExchange req) throws IOException {

        method = req.getRequestMethod();
        path = req.getRequestURI().toString();

        String response = "";
        int httpCode = 200;

        System.out.printf("%s %s %s \n", method, path, req.getRequestHeaders().keySet());
        if (existRoute(path)) {
            int n = routes.indexOf(path);
            Route route = routesMap.get(n);

            if(route.method.equals(method)) {
                response =  route.handler.execute(req);
            } else {
                response = "Method not enabled.";
                httpCode = 500;
            }

        } else {
            response = "Page not found.";
            httpCode = 404;
        }
        req.sendResponseHeaders(httpCode, response.length());
        OutputStream os = req.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

   public void setRoutes(ArrayList<Route> map)
   {
       Iterator it = map.iterator();
       while(it.hasNext())
       {
           Route route = (Route) it.next();
           routesMap.add(route);
           routes.add(route.url);
       }
   }

    public boolean existRoute(String url)
    {
        if (routes.contains(url)) {
            return true;
        }
        return false;
    }

}
