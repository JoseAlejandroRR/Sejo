package com.skillcorp.sejoframework.web;


import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.helpers.RuleValidator;

import java.util.HashMap;

public class Route {

    public String url;

    public String method;

    public IRequestHandler handler;

    public String[] middlewares;

    public String methodHandlerName;

    public HashMap<String, RuleValidator> rulesGet;

    public HashMap<String, RuleValidator> rulesPost;


    public Route(String method, String url, IRequestHandler handler)
    {
        this.method = method;
        this.url = url;
        this.handler = handler;
    }


    public Route(String method, String url, IRequestHandler handler, String methodName)
    {
        this.method = method;
        this.url = url;
        this.handler = handler;
        this.methodHandlerName = methodName;
    }
}
