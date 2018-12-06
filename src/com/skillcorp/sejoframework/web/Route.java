package com.skillcorp.sejoframework.web;


import com.sun.net.httpserver.HttpHandler;

public class Route {

    public String url;

    public String method;

    public IRequestHandler handler;

    public Route(String method, String url, IRequestHandler handler)
    {
        this.method = method;
        this.url = url;
        this.handler = handler;
    }
}
