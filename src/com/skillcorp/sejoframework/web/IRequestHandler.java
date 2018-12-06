package com.skillcorp.sejoframework.web;

import com.sun.net.httpserver.HttpExchange;

public interface IRequestHandler {

    public String execute(HttpExchange request);
}
