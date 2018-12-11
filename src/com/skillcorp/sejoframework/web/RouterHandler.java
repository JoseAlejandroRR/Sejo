package com.skillcorp.sejoframework.web;

import app.bootstrap.Middlewares;
import app.http.handlers.HomeHandler;
import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.contracts.http.IRequestValidator;
import com.skillcorp.sejoframework.contracts.http.IServer;
import com.skillcorp.sejoframework.contracts.http.IWebServer;
import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.skillcorp.sejoframework.contracts.providers.ILogger;
import com.skillcorp.sejoframework.helpers.Builder;
import com.skillcorp.sejoframework.helpers.Validator;
import com.skillcorp.sejoframework.providers.ServiceProvider;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouterHandler implements HttpHandler {

    private IWebServer server;

    private ILogger logger;

    private ServiceProvider container;

    private ArrayList<Route> routesMap;

    public ArrayList<String> routes;

    public static final String METHOD_POST = "POST";

    public static final String METHOD_GET = "GET";

    private IRequestHandler handler404;

    private long startTime, endTime, totalTime, startMemory, endMemory, totalMemory;

    private HashMap<String, String> routeParams;


    public RouterHandler(IWebServer server, ILogger logger)
    {
        this.server = server;
        this.logger = logger;
        routesMap = new ArrayList<Route>();
        routes = new ArrayList<String>();
        routes = new ArrayList<String>();
        routeParams = null;
    }

    public void setContainer(ServiceProvider container)
    {
        this.container = container;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        if(!server.isRunning()) return;

        startTime =  System.currentTimeMillis();
        startMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        routeParams = null;
        boolean prevent = false;
        boolean failValidator = false;


        Request request = new Request(exchange);
        Response response = new Response();

        logger.info(request.method, request.url, request.headers.keySet().toString());
        int index = existRoute(request.url);

        if (index >= 0) {
            //int n = routes.indexOf(request.url);
            Route route = routesMap.get(index);

            if(route.method.equals(request.method)) {

                if(route.middlewares != null && route.middlewares.length > 0){
                    //Response responseCheck = processMiddlewares(route, request, response);
                    processMiddlewares(route, request, response);
                    if (response.handled) {
                        prevent = true;
                    }
                }

                if(route.rulesGet != null && route.rulesGet.size() > 0 || route.rulesPost != null && route.rulesPost.size() > 0)
                {
                    HashMap<String, String> error = Validator.array(route.rulesGet, request.query);
                    String messageError = "";
                    if (error.size() > 0) {
                        for (HashMap.Entry<String, String> err: error.entrySet()) {
                            logger.debug("ERROR: "+err.getKey(), err.getValue());
                            if (!failValidator) {
                                messageError = err.getValue();
                            }
                        }
                        response.send(messageError, 500);
                    }
                }

                logger.debug("VALID: ",String.valueOf(prevent),String.valueOf(failValidator));

                if (!prevent || !failValidator) {
                    //logger.debug("IF VALA");
                    for(Map.Entry<String, String> item : routeParams.entrySet())
                    {
                        //logger.debug("var", item.getKey(), item.getValue());
                        request.query.put(item.getKey(),item.getValue());
                    }
                    //logger.debug("params", request.query.keySet().toString());
                    if (route.methodHandlerName==null) {
                        callMethodAtInstance(route.handler,"index", request, response);
                    } else {
                        callMethodAtInstance(route.handler,route.methodHandlerName, request, response);
                    }
                    //logger.debug("Working to here again");
                    //route.handler
                    //route.handler.execute(request, response);
                }
            } else {
                logger.info("Http Method not enabled: " + request.url,"httpCode: 505");
                response.send("Method not enabled.", 500);
            }

        } else {
            if (Server.getHandler404() == null) {
                response.send("URL not found.", 404);
            } else {
                callMethodAtInstance(Server.getHandler404(),"index", request, response);
            }
            logger.info("URL: " + request.url +" not found","httpCode: 404");
        }
        exchange.sendResponseHeaders(response.httpCode, response.getResponse().length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getResponse().getBytes());
        os.close();

        endTime   =  System.currentTimeMillis();
        endMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        totalTime = (endTime - startTime);
        totalMemory = (endMemory - startMemory);
        logger.info(String.format("Duration %d miliseconds using %s kb of memory \n", totalTime, totalMemory / 1000));
    }


   public void setRoutes(ArrayList<Route> map)
   {
       Iterator it = map.iterator();
       while(it.hasNext())
       {
           Route route = (Route) it.next();
           routesMap.add(route);
           routes.add(route.url);
       }
   }


    public int existRoute(String url)
    {
        Pattern p1 = Pattern.compile("\\[(.*?)\\]");
        Pattern p2 = null;
        Matcher matcher = null;
        String uri = url.split("\\?")[0].replaceAll("/","__").trim();
        Iterator it = routes.iterator();
        int n = 0;
        int many = 0;
        int k = 0;
        String[] keys = null;
        String[] values = null;
        int varTotals = 0;
        while(it.hasNext())
        {
            String path = (String) it.next();
            path = path.replaceAll("/","__").trim();
            matcher = p1.matcher(path);

            if(path.equals(uri)) return n;

            many = path.indexOf("[");
            keys = new String[0];
            if(many > 0) keys = new String[many];

            while(matcher.find()) {
                keys[k] = matcher.group().toString().replace("[","").replace("]","");
                //logger.debug("FOUND ", matcher.group());
                path = path.replace(matcher.group(), "(.*?)");
                k++;
            }
            //.debug("CHECKING " + uri, path);

            p2 = Pattern.compile(path);
            matcher = p2.matcher(uri);
            boolean b = false;
            while(matcher.find()) {
                b = true;
                //logger.debug("WORKS " + matcher.group());
            }

            if(b && uri.split("__").length==path.split("__").length) {
                logger.debug("ROUTE EXIST \n");

                String[] nodes = path.split("__");

                for(String node : nodes)
                {
                    if (node.trim().length() > 0) {
                        //logger.debug("KEY",node);
                        uri = uri.replace(node,"").replace("____","__");
                    }
                }

                String[] vars = uri.split("__");
                routeParams = new HashMap<String, String>();

                for(String var : vars)
                {
                    if (var.trim().length() > 0) {
                        //logger.debug("VALUE",var);
                        //uri = uri.replace(var,"");
                        varTotals++;
                    }
                }
                for(int i = 0; i < varTotals; i++)
                {
                    //logger.debug("add",keys[i],vars[i+1]);
                    routeParams.put(keys[i],vars[i+1]);
                }

                return n;
            } else {
                logger.debug("ROUTE NOT EXIST\n");
            }
            n++;
        }
        //logger.debug("__________________\n");
        //}
        return -1;
    }


    public RouterHandler get(String url, Object handler)
    {
        return get(url, handler, null);
    }

    public RouterHandler get(String url, Object handler, String methodName)
    {
        IRequestHandler handlerInstance = null;
        if (!handler.getClass().toString().contains("class")) return this;
        String className = handler.toString().split(" ")[1];
        //logger.debug("RUN "+className);
        try {
            handlerInstance = (IRequestHandler) Builder.createInstance(className);
            //handlerInstance.setContainer(container);
        } catch (Exception ex)
        {
            logger.debug("Handler Wrong: "+handler.getClass().getName());
        }

        if (handlerInstance != null) {

            if (this.existRoute(url) < 0) {
                routesMap.add(new Route(METHOD_GET, url, handlerInstance, methodName));
                routes.add(url);
            }

        }
        return this;
    }


    public void post(String url, IRequestHandler handler)
    {
        post(url, handler, null);
    }


    public void post(String url, IRequestHandler handler, String methodName)
    {
        if (existRoute(url) < 0) {
            routesMap.add(new Route(METHOD_POST, url, handler, methodName));
            routes.add(url);
        }
    }

    public RouterHandler middleware(String... middles)
    {
        String[] values = new String[middles.length];
        int i = 0;
        for(String key : middles)
        {
            values[i] = key;
            i++;
        }
        Route route = routesMap.get(routes.size()-1);
        route.middlewares = values;
        return this;
    }

    public RouterHandler validator(Object validator)
    {
        IRequestValidator validatorInstance = null;
        if (!validator.getClass().toString().contains("class")) return this;
        String className = validator.toString().split(" ")[1];

        try {
            validatorInstance = (IRequestValidator) Builder.createInstance(className);
        } catch (Exception ex)
        {
            logger.debug("Handler Wrong: "+validator.getClass().getName());
        }

        if (validatorInstance != null) {
            Route route = routesMap.get(routes.size()-1);
            route.rulesGet = validatorInstance.getRulesGet();
            route.rulesPost = validatorInstance.getRulesPost();
        }
        return this;
    }

    private Response processMiddlewares(Route route, Request request, Response response)
    {
        Response res = null;
        for (String key : route.middlewares)
        {
            IMiddleware middleware = server.getMiddlware(key);

            if(middleware != null)
            {
                middleware.handle(request, response);
            } else {
                return response.send("Middleware Not Found", 500);
            }
        }

        return res;
    }

    private void callMethodAtInstance(Object obj, String methodName, Request request, Response response)
    {
        Method method = null;
        try {
            method = obj.getClass().getMethod(methodName, Request.class, Response.class);
        } catch (SecurityException e) {
            logger.debug("Cant execute method by: " + e.getMessage());
        }
        catch (NoSuchMethodException e) {
            logger.debug("Method not exists: " + e.getMessage());
        }

        try {
            method.invoke(obj, request, response);
        } catch (IllegalArgumentException e) {
            logger.debug("Error calling method: " + e.getMessage());
        }
        catch (IllegalAccessException e) {
            logger.debug("Error: " + e.getMessage());

        }
        catch (InvocationTargetException e) {
            logger.debug("Error: " + e.getMessage());
        }
    }


}
