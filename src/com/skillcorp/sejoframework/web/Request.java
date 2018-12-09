package com.skillcorp.sejoframework.web;

import com.skillcorp.sejoframework.helpers.Builder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.util.HashMap;

public class Request {

    public String method;

    public Headers headers;

    public HashMap<String, String> query;

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

        System.out.println("URL: "+url);
        String[] segments = this.url.split("\\?");
        if(segments.length>1) {
            this.query = Builder.getParamsFromUrl(segments[1]);
        } else {
            this.query = new HashMap<String,  String>();
        }
    }
}
