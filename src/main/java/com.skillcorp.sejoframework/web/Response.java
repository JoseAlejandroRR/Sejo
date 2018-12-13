package com.skillcorp.sejoframework.web;

import com.skillcorp.sejoframework.helpers.Builder;
import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;

public class Response {

    private String response;

    public int httpCode = 200;

    public boolean handled = false;

    private HttpExchange httpExchange;

    private static final String CONTENT_TYPE_HTML = "text/html";

    private static final String CONTENT_TYPE_JSON = "application/json";

    private String content_type;

    public Response(HttpExchange exchange)
    {
        this.httpExchange = exchange;
    }

    public Response send(String response)
    {
        return send(response, httpCode);
    }

    public Response send(int httpCode)
    {
        return send("", httpCode);
    }

    public Response send(String response, int httpCode)
    {
        this.httpCode = httpCode;

        if (this.response == null)
        {
            this.response = response;
        }

        if(content_type == null)
        {
            content_type = CONTENT_TYPE_HTML;
        }

        httpExchange.getResponseHeaders().add("Content-Type", content_type);

        handled = true;

        return this;
    }

    public String getResponse()
    {
        return response;
    }

    public void json(HashMap obj)
    {
        json(obj, httpCode);
    }

    public void json(HashMap obj, int httpCode)
    {
        String json = Builder.convertHashMapToJson(obj);

        content_type = CONTENT_TYPE_JSON;

        send(json, httpCode);
    }
}
