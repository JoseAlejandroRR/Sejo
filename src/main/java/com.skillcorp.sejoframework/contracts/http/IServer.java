package com.skillcorp.sejoframework.contracts.http;

import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.skillcorp.sejoframework.contracts.providers.ILogger;
import com.skillcorp.sejoframework.providers.ServiceProvider;

public interface IServer {

    public void start(int port);

    public void stop();

    //public void setLogger(ILogger logger);

    public boolean isRunning();
}
