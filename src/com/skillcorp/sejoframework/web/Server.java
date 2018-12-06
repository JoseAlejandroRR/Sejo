package com.skillcorp.sejoframework.web;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class Server  {

    public static int PORT;

    private static HttpServer httpServer = null;

    private static Server instance = null;

    private static ArrayList<Route> routesMap;

    public static ArrayList<String> routes;

    private static RequestHandler requestHandler;

    public static final String METHOD_POST = "POST";

    public static final String METHOD_GET = "GET";

    private Server(int port)
    {
        routesMap = new ArrayList<Route>();
        routes = new ArrayList<String>();
        try {
            this.PORT = port;
            httpServer =  HttpServer.create(new InetSocketAddress(PORT), 0);
        } catch (IOException e) {
            System.out.println("SERVER_CREATE_ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Server getInstance(int port)
    {
        if(instance == null) {
            instance = new Server(port);
        }
        return instance;
    }

    public static void get(String url, IRequestHandler handler)
    {
        if (!existRoute(url)) {
            //httpServer.createContext(url, handler);
            routesMap.add(new Route(METHOD_GET, url, handler));
            routes.add(url);
        }
    }

    public static void post(String url, IRequestHandler handler)
    {
        if (!existRoute(url)) {
            //httpServer.createContext(url, handler);
            routesMap.add(new Route(METHOD_POST, url, handler));
            routes.add(url);
        }
    }

    public static void start()
    {
        requestHandler = new RequestHandler();
        requestHandler.setRoutes(routesMap);

        httpServer.createContext("/", requestHandler);
        httpServer.setExecutor(null);
        httpServer.start();
    }

    public static boolean existRoute(String url)
    {
        if (routes.contains(url)) {
            return true;
        }
        return false;
    }

}
