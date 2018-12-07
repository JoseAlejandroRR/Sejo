package com.skillcorp.sejoframework.web;

import com.sun.net.httpserver.HttpExchange;

public class Response {

    private String response;

    public int httpCode = 200;

    public Response()
    {
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
        return this;
    }

    public String getResponse()
    {
        return response;
    }
}
