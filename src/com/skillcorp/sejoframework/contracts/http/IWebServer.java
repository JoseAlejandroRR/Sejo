package com.skillcorp.sejoframework.contracts.http;

import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;

public interface IWebServer {

    public static IRequestHandler getHandler404() {
        return null;
    }

    public IMiddleware getMiddlware(String key);

    public boolean isRunning();
}
