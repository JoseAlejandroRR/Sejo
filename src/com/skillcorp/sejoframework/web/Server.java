package com.skillcorp.sejoframework.web;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Server  {

    public static int PORT;

    private static HttpServer httpServer = null;

    private static Server instance = null;

    private static ArrayList<Route> routesMap;

    public static ArrayList<String> routes;

    public static RouterHandler routerHandler;


    private Server(int port)
    {
        this.PORT = port;
        routesMap = new ArrayList<Route>();
        routes = new ArrayList<String>();
        routerHandler = new RouterHandler();
        try {

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



    public static void start()
    {
        //routerHandler.setRoutes(routesMap);

        httpServer.createContext("/", routerHandler);
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
