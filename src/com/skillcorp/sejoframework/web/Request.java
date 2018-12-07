package com.skillcorp.sejoframework.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;

public class Request {

    public String method;

    public Headers headers;

    public InputStream body;

    public String url;

    public String protocol;

    public Request(HttpExchange exchange)
    {
        this.url = exchange.getRequestURI().toString();
        this.method = exchange.getRequestMethod();
        this.headers = exchange.getRequestHeaders();
        this.body = exchange.getRequestBody();
        this.protocol = exchange.getProtocol();
    }
}
