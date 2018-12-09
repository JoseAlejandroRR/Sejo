package com.skillcorp.sejoframework;

import app.bootstrap.Providers;
import com.skillcorp.sejoframework.contracts.http.IServer;
import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.skillcorp.sejoframework.contracts.providers.ILogger;
import com.skillcorp.sejoframework.helpers.Builder;
import com.skillcorp.sejoframework.providers.Provider;
import com.skillcorp.sejoframework.contracts.app.IApplication;
import com.skillcorp.sejoframework.providers.ServiceProvider;
import com.skillcorp.sejoframework.web.Server;

import java.util.HashMap;
import java.util.Map;

public class Application implements IApplication {


    private static Application instance;

    private static ServiceProvider container;

    private static HashMap<String, Provider> providers;

    public IServer server;

    public static int PORT = 9000;

    public ILogger logger;


    public Application(IServer server, ServiceProvider containr, ILogger logger)
    {
        providers = new HashMap<String, Provider>();

        this.container = containr;

        this.server =  server;

        this.logger = logger;
    }

    /*public static Application getInstance()
    {
        if(instance == null){
            instance = new Application();
        }
        return instance;
    }*/

    public void run()
    {
        //server.start();
        ///System.out.println("Server start at http://localhost:" + PORT);
    }

    /*public void registerMiddlewares(Map<String, String> instances)
    {
        //Map<String, String> instances = new HashMap<>();
        for (Map.Entry<String, String> provider : instances.entrySet())
        {
            System.out.println("CREATE INSTANCE FOR: " + provider.getValue());
            Provider instance = new Provider();
            //DB db = (DB) ;

            //instance.create();
            //System.out.println("DB SAY: " + db.getClass().toString());
            //System.out.println("DB SAY: " + ((DB)instance.getInstance()).getMessage());
            registerMiddleware(provider.getKey(), (IMiddleware) createInstance(provider.getValue()));
        }

    }*/

    public static ServiceProvider container()
    {
        return container;
    }



}
