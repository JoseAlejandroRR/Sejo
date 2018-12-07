package com.skillcorp.sejoframework.web;


import com.skillcorp.sejoframework.contracts.http.IRequestHandler;

public class Route {

    public String url;

    public String method;

    public IRequestHandler handler;

    public String[] middlewares;

    //public String middleware;

    public Route(String method, String url, IRequestHandler handler)
    {
        this.method = method;
        this.url = url;
        //this.middleware = null;
        this.handler = handler;
    }

    public Route(String method, String url, String middleware, IRequestHandler handler)
    {
        this.method = method;
        this.url = url;
        this.handler = handler;

        if(middleware != null) {
            this.middlewares = new String[1];
            this.middlewares[0] = middleware;
        }

    }

    public Route(String method, String url, String[] middlewares, IRequestHandler handler)
    {
        this.method = method;
        this.url = url;
        this.middlewares = middlewares;
        this.handler = handler;
    }
}
