package com.skillcorp.sejoframework;

import app.bootstrap.Providers;
import app.http.handlers.HomeHandler;
import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.skillcorp.sejoframework.contracts.providers.Provider;
import com.skillcorp.sejoframework.contracts.app.IApplication;
import com.skillcorp.sejoframework.web.RouterHandler;
import com.skillcorp.sejoframework.web.Server;

import java.util.HashMap;
import java.util.Map;

public class Application implements IApplication {


    private static Application instance;

    private static HashMap<String, Provider> providers;
    private static HashMap<String, IMiddleware> middlewares;

    public Server server;

    public static int PORT = 9000;


    private Application()
    {
        server = Server.getInstance(PORT);

        providers = new HashMap<String, Provider>();
        middlewares = new HashMap<String, IMiddleware>();

    }

    public static Application getInstance()
    {
        if(instance == null){
            instance = new Application();
        }
        return instance;
    }

    public void run()
    {
        server.start();
        System.out.println("Server start at http://localhost:" + PORT);
    }

    public void registerMiddlewares(Map<String, String> instances)
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

    }

    public void registerProviders(Map<String, String> instances)
    {
        //Map<String, String> instances = new HashMap<>();
        for (Map.Entry<String, String> provider : instances.entrySet())
        {
            System.out.println("CREATE INSTANCE FOR: " + provider.getValue());
            Provider instance = new Provider();
            //DB db = (DB) ;

            instance.create(createInstance(provider.getValue()));
            //System.out.println("DB SAY: " + db.getClass().toString());
            //System.out.println("DB SAY: " + ((DB)instance.getInstance()).getMessage());
            setProvider(provider.getKey(), instance);
        }

    }

    public static Object getProvider(String key)
    {
        System.out.println("CALL PROVIDER: " + key);
        if (providers.containsKey(key)) {
            return providers.get(key).getInstance();
        } else {
            System.out.println("PROVIDER NOT FOUND: " + key);
        }
        return null;
    }

    private void setProvider(String key, Provider instance)
    {
        System.out.println("PROVIDER: " + key);
        providers.put(key, instance);
    }

    public static IMiddleware getMiddlware(String key)
    {
        System.out.println("CALL MIDDLEWARE: " + key);
        if (middlewares.containsKey(key)) {
            return middlewares.get(key);
        } else {
            System.out.println("MIDDLEWARE NOT FOUND: " + key);
        }
        return null;
    }

    private void registerMiddleware(String key, IMiddleware middleware)
    {
        System.out.println("MDDLEWARE: " + key);
        middlewares.put(key, middleware);
    }

    private Object createInstance(String className) {
        Object object = null;
        try {
            Class classDefinition = Class.forName(className);
            object = classDefinition.newInstance();
        } catch (InstantiationException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        return object;
    }
}
