package com.skillcorp.sejoframework.web;

import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.providers.ServiceProvider;

public class RequestHandler implements IRequestHandler {

    public ServiceProvider container;

    protected ServiceProvider getContainer()
    {
        return Application.container();
    }

    /*public void setContainer(ServiceProvider container)
    {
        this.container = container;
    }*/

}
