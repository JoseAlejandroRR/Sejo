package com.skillcorp.sejoframework.contracts.middlewares;

import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.Response;

public interface IMiddleware {

    public void handle(Request request, Response response);
}
