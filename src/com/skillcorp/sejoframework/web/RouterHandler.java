package com.skillcorp.sejoframework.web;

import app.bootstrap.Middlewares;
import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class RouterHandler implements HttpHandler {

    private ArrayList<Route> routesMap;

    public ArrayList<String> routes;

    public static final String METHOD_POST = "POST";

    public static final String METHOD_GET = "GET";

    private IRequestHandler handler404;

    private long startTime, endTime, totalTime, startMemory, endMemory, totalMemory;


    public RouterHandler()
    {
        routesMap = new ArrayList<Route>();
        routes = new ArrayList<String>();
    }


    public void setHandler404(IRequestHandler handler404)
    {
        this.handler404 = handler404;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        startTime =  System.currentTimeMillis();
        startMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        boolean prevent = false;

        Request request = new Request(exchange);
        Response response = new Response();

        System.out.printf("%s %s %s \n", request.method, request.url, request.headers.keySet());

        if (existRoute(request.url)) {
            int n = routes.indexOf(request.url);
            Route route = routesMap.get(n);

            if(route.method.equals(request.method)) {

                if(route.middlewares.length > 0){
                    Response responseCheck = processMiddlewares(route, request, response);
                    if (responseCheck != null) {
                        prevent = true;
                    }
                }
                if (!prevent) {
                    response =  route.handler.execute(request, response);
                }
            } else {
                response.send("Method not enabled.", 500);
            }

        } else {
            if (handler404 == null) {
                response.send("URL not found.", 404);
            } else {
                response = handler404.execute(request, response);
            }
        }
        exchange.sendResponseHeaders(response.httpCode, response.getResponse().length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getResponse().getBytes());
        os.close();

        endTime   =  System.currentTimeMillis();
        endMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        totalTime = (endTime - startTime);
        totalMemory = (endMemory - startMemory);
        System.out.printf("Duration %d miliseconds using %d memory \n", totalTime, totalMemory);
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


    public  void get(String url, IRequestHandler handler)
    {
        get(url, new String[]{}, handler);
    }


    public  void get(String url, String middleware, IRequestHandler handler)
    {
        get(url, new String[] {middleware}, handler);
    }


    public  void get(String url, String[] middlewares, IRequestHandler handler)
    {
        if (!this.existRoute(url)) {
            routesMap.add(new Route(METHOD_GET, url, middlewares, handler));
            routes.add(url);
        }
    }


    public  void post(String url, IRequestHandler handler)
    {
        post(url, null, handler);
    }


    public  void post(String url, String middleware, IRequestHandler handler)
    {
        if (!existRoute(url)) {
            routesMap.add(new Route(METHOD_POST, url, middleware, handler));
            routes.add(url);
        }
    }

    private Response processMiddlewares(Route route, Request request, Response response)
    {
        Response res = null;
        for (String key : route.middlewares)
        {
            IMiddleware middleware = Application.getMiddlware(key);

            if(middleware != null)
            {
                res = middleware.handle(request, response);
            } else {
                return response.send("Middleware Not Found", 500);
            }
        }

        return res;
    }


}
