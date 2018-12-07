package com.skillcorp.sejoframework.contracts.http;

import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.Response;
import com.sun.net.httpserver.HttpExchange;

public interface IRequestHandler {

    public Response execute(Request request, Response response);
}
